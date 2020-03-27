package com.hll_sc_app.bean.wallet;

import java.util.List;

public class OcrImageResp {

    private String imgLPIDCardFront;
    private String imgLPIDCardBack;
    private String imgLicense;

    public String getImgLPIDCardFront() {
        return imgLPIDCardFront;
    }

    public void setImgLPIDCardFront(String imgLPIDCardFront) {
        this.imgLPIDCardFront = imgLPIDCardFront;
    }

    public String getImgLPIDCardBack() {
        return imgLPIDCardBack;
    }

    public void setImgLPIDCardBack(String imgLPIDCardBack) {
        this.imgLPIDCardBack = imgLPIDCardBack;
    }

    public String getImgLicense() {
        return imgLicense;
    }

    public void setImgLicense(String imgLicense) {
        this.imgLicense = imgLicense;
    }

    public static class ImgLPIDCardFrontBean {

        private String config_str;
        private String address;
        private String nationality;
        private boolean success;
        private String num;
        private String sex;
        private String name;
        private String birth;
        private String request_id;
        private FaceRectBean face_rect;
        private List<CardRegionBean> card_region;
        private List<FaceRectVerticesBean> face_rect_vertices;

        public String getConfig_str() {
            return config_str;
        }

        public void setConfig_str(String config_str) {
            this.config_str = config_str;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public FaceRectBean getFace_rect() {
            return face_rect;
        }

        public void setFace_rect(FaceRectBean face_rect) {
            this.face_rect = face_rect;
        }

        public List<CardRegionBean> getCard_region() {
            return card_region;
        }

        public void setCard_region(List<CardRegionBean> card_region) {
            this.card_region = card_region;
        }

        public List<FaceRectVerticesBean> getFace_rect_vertices() {
            return face_rect_vertices;
        }

        public void setFace_rect_vertices(List<FaceRectVerticesBean> face_rect_vertices) {
            this.face_rect_vertices = face_rect_vertices;
        }

        public static class FaceRectBean {
            /**
             * size : {"width":116.56238555908203,"height":125.2462387084961}
             * center : {"x":638.8016357421875,"y":265.28076171875}
             * angle : -1.4464112520217896
             */

            private SizeBean size;
            private CenterBean center;
            private double angle;

            public SizeBean getSize() {
                return size;
            }

            public void setSize(SizeBean size) {
                this.size = size;
            }

            public CenterBean getCenter() {
                return center;
            }

            public void setCenter(CenterBean center) {
                this.center = center;
            }

            public double getAngle() {
                return angle;
            }

            public void setAngle(double angle) {
                this.angle = angle;
            }

            public static class SizeBean {
                /**
                 * width : 116.56238555908203
                 * height : 125.2462387084961
                 */

                private double width;
                private double height;

                public double getWidth() {
                    return width;
                }

                public void setWidth(double width) {
                    this.width = width;
                }

                public double getHeight() {
                    return height;
                }

                public void setHeight(double height) {
                    this.height = height;
                }
            }

            public static class CenterBean {
                /**
                 * x : 638.8016357421875
                 * y : 265.28076171875
                 */

                private double x;
                private double y;

                public double getX() {
                    return x;
                }

                public void setX(double x) {
                    this.x = x;
                }

                public double getY() {
                    return y;
                }

                public void setY(double y) {
                    this.y = y;
                }
            }
        }

        public static class CardRegionBean {
            /**
             * x : 131
             * y : 91
             */

            private double x;
            private double y;

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }
        }

        public static class FaceRectVerticesBean {
            /**
             * x : 582.1197509765625
             * y : 329.35504150390625
             */

            private double x;
            private double y;

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }
        }
    }

    public static class ImgLPIDCardBackBean {
        /**
         * config_str : {"side":"back"}
         * end_date : 20250904
         * issue : 济南市公安局槐荫分局
         * success : true
         * request_id : 20191213152838_f75f674d6526dd5f019fa993cbaa8938
         * card_region : [{"x":131,"y":95}]
         * start_date : 20050904
         */

        private String config_str;
        private String end_date;
        private String issue;
        private boolean success;
        private String request_id;
        private String start_date;
        private List<CardRegionBeanX> card_region;

        public String getConfig_str() {
            return config_str;
        }

        public void setConfig_str(String config_str) {
            this.config_str = config_str;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public List<CardRegionBeanX> getCard_region() {
            return card_region;
        }

        public void setCard_region(List<CardRegionBeanX> card_region) {
            this.card_region = card_region;
        }

        public static class CardRegionBeanX {
            /**
             * x : 131
             * y : 95
             */

            private double x;
            private double y;

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }
        }
    }

    public static class ImgLicenseBean {
        private String capital;
        private String address;
        private String reg_num;
        private String business;
        private String valid_period;
        private QrcodeBean qrcode;
        private StampBean stamp;
        private TitleBean title;
        private String type;
        private String person;
        private boolean success;
        private String name;
        private double angle;
        private String establish_date;
        private String captial;
        private EmblemBean emblem;
        private String request_id;

        public String getCapital() {
            return capital;
        }

        public void setCapital(String capital) {
            this.capital = capital;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReg_num() {
            return reg_num;
        }

        public void setReg_num(String reg_num) {
            this.reg_num = reg_num;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getValid_period() {
            return valid_period;
        }

        public void setValid_period(String valid_period) {
            this.valid_period = valid_period;
        }

        public QrcodeBean getQrcode() {
            return qrcode;
        }

        public void setQrcode(QrcodeBean qrcode) {
            this.qrcode = qrcode;
        }

        public StampBean getStamp() {
            return stamp;
        }

        public void setStamp(StampBean stamp) {
            this.stamp = stamp;
        }

        public TitleBean getTitle() {
            return title;
        }

        public void setTitle(TitleBean title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public String getEstablish_date() {
            return establish_date;
        }

        public void setEstablish_date(String establish_date) {
            this.establish_date = establish_date;
        }

        public String getCaptial() {
            return captial;
        }

        public void setCaptial(String captial) {
            this.captial = captial;
        }

        public EmblemBean getEmblem() {
            return emblem;
        }

        public void setEmblem(EmblemBean emblem) {
            this.emblem = emblem;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public static class QrcodeBean {
            /**
             * top : 793
             * left : 102
             * width : 122
             * height : 126
             */

            private String top;
            private String left;
            private String width;
            private String height;

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getLeft() {
                return left;
            }

            public void setLeft(String left) {
                this.left = left;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }

        public static class StampBean {
            /**
             * top : 821
             * left : 494
             * width : 156
             * height : 153
             */

            private String top;
            private String left;
            private String width;
            private String height;

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getLeft() {
                return left;
            }

            public void setLeft(String left) {
                this.left = left;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }

        public static class TitleBean {
            /**
             * top : 259
             * left : 174
             * width : 385
             * height : 78
             */

            private String top;
            private String left;
            private String width;
            private String height;

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getLeft() {
                return left;
            }

            public void setLeft(String left) {
                this.left = left;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }

        public static class EmblemBean {
            /**
             * top : 108
             * left : 311
             * width : 130
             * height : 136
             */

            private String top;
            private String left;
            private String width;
            private String height;

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getLeft() {
                return left;
            }

            public void setLeft(String left) {
                this.left = left;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }
    }
}
