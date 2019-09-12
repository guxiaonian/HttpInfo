package com.example.httpinfo.adapter;

import android.content.Context;

import com.example.httpinfo.R;
import com.example.httpinfo.ResultBean;


/**
 * Created by gunaonian on 2018/3/30.
 */

public class ResultAdapter extends BaseRecyclerAdapter<ResultBean> {

    public ResultAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void convert(BaseRecyclerHolder baseRecyclerHolder, ResultBean item, int position) {
        baseRecyclerHolder.setText(R.id.item_activity_result_rv_tv, item.getTitle());
        baseRecyclerHolder.setText(R.id.item_activity_result_rv_tv_param, formatString(item.getParam().toString()));
    }

    public static String formatString(String text) {

        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append(indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }
}
