package com.hll_sc_app.app.message.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.ImageViewActivity;
import com.hll_sc_app.base.utils.permission.RequestPermissionUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.message.DefaultUser;
import com.hll_sc_app.bean.message.IMBean;
import com.hll_sc_app.bean.message.MessageBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.IMConnection;
import com.hll_sc_app.widget.TitleBar;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.android.AndroidSmackInitializer;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jiguang.imui.chatinput.ChatInputView;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.menu.Menu;
import cn.jiguang.imui.chatinput.menu.MenuManager;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

@Route(path = RouterConfig.MESSAGE_CHAT)
public class MessageChatActivity extends BaseLoadActivity implements IMessageChatContract.IMessageChatView {
    private static final int REQUEST_CODE_CHOOSE = 102;
    private static final String[] PERMISSIONS = {Permission.CAMERA
            , Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE};
    @BindView(R.id.amc_list)
    MessageList mList;
    @BindView(R.id.amc_input)
    ChatInputView mInput;
    @BindView(R.id.amc_title_bar)
    TitleBar mTitleBar;
    @Autowired(name = "parcelable")
    MessageBean mBean;
    private String groupID;
    private String employeeID;
    private MultiUserChat multiUserChat;
    private MsgListAdapter<IMBean> mAdapter;
    private IMessageChatContract.IMessageChatPresenter mPresenter;
    private IMConnection imConnection;
    private InputMethodManager mImm;
    private Window mWindow;

    public static void start(MessageBean bean) {
        RouterUtil.goToActivity(RouterConfig.MESSAGE_CHAT, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_message_chat);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        this.mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mWindow = getWindow();
        initView();
        initData();
    }

    private void initData() {
        mPresenter = MessageChatPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.clearUnRead(mBean.getTopic());
    }

    private void initView() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            groupID = userBean.getGroupID();
            employeeID = userBean.getEmployeeID();
        } else {
            finish();
        }
        initChartInputView();
        initMessageListView();
        startChat();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imConnection != null) {
            imConnection.closeConnection();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initChartInputView() {
        mList.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mInput.getMenuState() == View.VISIBLE) {
                        mInput.dismissMenuLayout();
                    }
                    try {
                        View v = getCurrentFocus();
                        if (mImm != null && v != null) {
                            mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            view.clearFocus();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        });
        mInput.setMenuContainerHeight(GlobalPreference.getParam(Constants.KEYBOARD_KEY, 535));
        mInput.setMenuClickListener(new ChatMenuClickListener());
        mInput.getInputView().setOnTouchListener((view, motionEvent) -> {
            scrollToBottom();
            return false;
        });
        MenuManager menuManager = mInput.getMenuManager();
        menuManager.setMenu(Menu
                .newBuilder()
                .customize(true)
                .setRight(Menu.TAG_SEND)
                .setBottom(Menu.TAG_GALLERY, Menu.TAG_EMOJI)
                .build());
    }

    /**
     * 列表滑动到底部
     */
    private void scrollToBottom() {
        new Handler().postDelayed(() -> {
            if (mList.canScrollVertically(1)) {
                mList.smoothScrollToPosition(0);
            }
        }, 200);
    }

    private void initMessageListView() {
        mTitleBar.setHeaderTitle(mBean.getName());
        mAdapter = new MsgListAdapter<>(employeeID, new GlideMsgImageLoader(this));
        mAdapter.setOnMsgClickListener(message -> {
            if (message.getType() == IMessage.MessageType.RECEIVE_IMAGE.ordinal()
                    || message.getType() == IMessage.MessageType.SEND_IMAGE.ordinal()) {
                Intent intent = new Intent(MessageChatActivity.this, ImageViewActivity.class);
                intent.putExtra("url", message.getContent());
                startActivity(intent);
            }
        });
        mList.setAdapter(mAdapter);
    }

    /**
     * 启动聊天
     */
    private void startChat() {
        AndroidSmackInitializer initializer = new AndroidSmackInitializer();
        initializer.initialize();
        Observable.timer(0, TimeUnit.MILLISECONDS, Schedulers.single())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner()))).subscribe((Consumer<Object>) o -> {
            imConnection = IMConnection.getInstance();
            multiUserChat = imConnection.joinMultiUserChat(employeeID, mBean.getTopic(), this::handleMessage);
        });
    }

    /**
     * 处理群聊消息
     *
     * @param message 收到的群聊消息
     */
    private void handleMessage(Message message) {
        if (TextUtils.isEmpty(message.getBody())) {
            return;
        }
        IMBean item = JsonUtil.fromJson(message.getBody(), IMBean.class);
        if (item == null || "掌柜您好，有任何需求都可以对我说哦~".equals(item.getContent())) {
            return;
        }
        if (TextUtils.equals(item.getGroupID(), groupID)) {
            // 我发送的消息
            item.setType(TextUtils.equals(item.getDataType(), IMBean.TYPE_IMAGE) ?
                    IMessage.MessageType.SEND_IMAGE.ordinal() : IMessage.MessageType.SEND_TEXT.ordinal());
            item.setUser(new DefaultUser(mBean.getReceiver(), mBean.getReveiverName(),
                    TextUtils.isEmpty(mBean.getReceiverLogo()) ? "R.drawable.aurora_headicon_default" : mBean.getReceiverLogo()));
        } else {
            // 别人发送过来的消息
            item.setType(TextUtils.equals(item.getDataType(), IMBean.TYPE_IMAGE) ?
                    IMessage.MessageType.RECEIVE_IMAGE.ordinal() : IMessage.MessageType.RECEIVE_TEXT.ordinal());
            item.setUser(new DefaultUser(mBean.getSender(), mBean.getName()
                    , TextUtils.isEmpty(mBean.getImgUrl()) ? "R.drawable.aurora_headicon_default" : mBean.getImgUrl()));
        }
        runOnUiThread(() -> mAdapter.addToStart(item, true));
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        new RequestPermissionUtils(this, PERMISSIONS, this::selectPhoto).requestPermission();
    }

    private void selectPhoto() {
        UIUtils.selectPhoto(this, REQUEST_CODE_CHOOSE, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                List<String> list = Matisse.obtainPathResult(data);
                if (!CommonUtils.isEmpty(list)) {
                    mPresenter.imageUpload(list.get(0));
                }
            }
        }
    }

    @Override
    public void uploadSuccess(String path) {
        sendMessage(path, IMBean.TYPE_IMAGE);
    }

    /**
     * 发送消息
     *
     * @param message message
     */
    private void sendMessage(String message, String dataType) {
        if (multiUserChat != null) {
            try {
                IMBean bean = new IMBean();
                bean.setDataType(dataType);
                bean.setGroupID(groupID);
                bean.setSender(employeeID);
                bean.setSendTime(CalendarUtils.format(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
                bean.setContent(message);
                bean.setFrom(1);
                bean.setServiceType(0);
                multiUserChat.sendMessage(JsonUtil.toJson(bean));
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 聊天菜单监听器
     */
    private class ChatMenuClickListener implements OnMenuClickListener {
        @Override
        public boolean onSendTextMessage(CharSequence input) {
            if (TextUtils.isEmpty(input)) {
                return true;
            }
            sendMessage(String.valueOf(input), IMBean.TYPE_TEXT);
            scrollToBottom();
            return true;
        }

        @Override
        public void onSendFiles(List<FileItem> list) {
            // no-op
        }

        @Override
        public boolean switchToMicrophoneMode() {
            return false;
        }

        @Override
        public boolean switchToGalleryMode() {
            requestPermission();
            return false;
        }

        @Override
        public boolean switchToCameraMode() {
            return false;
        }

        @Override
        public boolean switchToEmojiMode() {
            scrollToBottom();
            return true;
        }
    }
}
