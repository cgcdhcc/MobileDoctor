package com.imedical.mobiledoctor.base;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.imedical.mobiledoctor.R;


/**
 * Created by dashan on 2017/9/25.
 */

public class PdfViewerActivity extends BaseActivity {
    public PDFView pdfView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.pdfview_activity);
        String url = getIntent().getStringExtra("filepath");
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromUri(Uri.parse(url))

                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                .spacing(0)
                .load();
    }
}
