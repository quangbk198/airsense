package com.example.doan1.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doan1.R;

public class AboutUsFragment extends Fragment {
    View view;
    TextView tvLinkWeb, tvDescription, tvLinkAirWEB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aboutus, container, false);

        initWidget();

        String html_lab = "<h1> GIỚI THIỆU PHÒNG LAB </h1>"
                + "<p> SPARC Labratory đặt tại tầng 618, thư viện Tạ Quang Bửu, Trường đại học Bách Khoa, số 1 Đại Cồ Việt, Bách Khoa, Hai Bà Trưng, Hà Nội<p>"
                + "<p> - Giảng viên hướng dẫn: TS. Hàn Huy Dũng <p>"
                + "<h1> MỤC ĐÍCH DỰ ÁN </h1>"
                + "<p> - Dự án AirSENSE dành cho học sinh, sinh viên phát triển các kỹ năng STEM với tư duy bảo vệ môi trường. <p>"
                + "<p> - Với mục tiêu phát triển giáo dục về tư duy và kỹ năng, Airsense hướng tới các hoạt động khoa học công dân và ứng dụng thực tế. <p>"
                + "<h1> HOẠT ĐỘNG </h1>"
                + "<p> - Phát triển công cụ đo và cải thiện chất lượng không khí <p>"
                + "<p> - Hỗ trợ học sinh – sinh viên và cộng đồng nghiên cứu ứng dụng IoT trong lĩnh vực môi trường <p>"
                + "<p> - Hợp tác với các đối tác, đồng nghiệp thực hiện các dự án khoa học công dân và tổ chức cuộc thi trong lĩnh vực công nghệ và môi trường. <p>"
                + "<p> - Phối hợp và tổ chức lớp học STEM dành cho học sinh và sinh viên. <p>"
                + "<h1> WEBSITE </h1>"
                + "Để biết thêm chi tiết, xem tại:";

        tvDescription.setText(android.text.Html.fromHtml(html_lab));

        clickWebsite();

        return view;
    }

    private void initWidget() {
        tvLinkWeb = view.findViewById(R.id.link_web);
        tvDescription = view.findViewById(R.id.textviewDescription);
        tvLinkAirWEB = view.findViewById(R.id.link_web_airsense);
    }

    private void clickWebsite() {
        final String URL1 = "http://" + tvLinkWeb.getText().toString().trim();
        final String URL2 = "http://" + tvLinkAirWEB.getText().toString().trim();

        tvLinkWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(URL1));
                startActivity(browserIntent1);
            }
        });

        tvLinkAirWEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(URL2));
                startActivity(browserIntent2);
            }
        });
    }
}
