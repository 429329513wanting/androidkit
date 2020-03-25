package com.sendinfo.androidkit.module.demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;

import java.io.File;

import butterknife.BindView;

public class DemoActivity extends BaseMVPActivity {

    @BindView(R.id.photo_img)
    ImageView img;
    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView() {

        openAlbum();
    }
    //
    File newFile;
    private void openAlbum(){

        Uri photoUri = null;
        newFile = new File(getExternalCacheDir(),"output_image.jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            photoUri = FileProvider.getUriForFile(this,"com.sendinfo.androidkit.FileProvider",newFile);

        }else {

            photoUri = Uri.fromFile(newFile);

        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
        startActivityForResult(intent,0);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK){

            if (requestCode == 0){

                //读取路径的图片
                LogUtils.d(newFile.getAbsoluteFile());
                img.setImageBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));


            }
        }
    }
}

