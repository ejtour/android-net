var url = location.search.substring(1);
pdfjsLib.workerSrc = 'pdf.worker.js';

var pdfDoc = null;

function createPage() {
    var div = document.createElement("canvas");
    document.body.appendChild(div);
    return div;
}

function renderPage(num) {
    pdfDoc.getPage(num).then(function (page) {
        var viewport = page.getViewport(2.0);
        var canvas = createPage();
        var ctx = canvas.getContext('2d');

        canvas.height = viewport.height;
        canvas.width = viewport.width;

        page.render({
            canvasContext: ctx,
            viewport: viewport
        });
        if(num==1){
        //关闭等待
          showLoadinginNative(false);
        }
    });
}

function showLoadinginNative(isLoading){
    JSBridge.showLoading(isLoading);
}

//显示等待
showLoadinginNative(true);

pdfjsLib.getDocument(url).promise.then(function (pdf) {
    pdfDoc = pdf;

    for (var i = 1; i <= pdfDoc.numPages; i++) {
        renderPage(i)
    }
});

