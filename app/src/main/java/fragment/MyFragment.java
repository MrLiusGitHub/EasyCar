package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ldc.easycar.R;

/**
 * Created by ldc on 2016/6/5.
 */
public class MyFragment extends Fragment{

    public static MyFragment getInstance(String str){
        MyFragment fragment=new MyFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",str);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.test_fragment_item,null);
        TextView textView= (TextView) view.findViewById(R.id.textview);
        Bundle bundle=getArguments();
        textView.setText(bundle.get("url").toString());

        return view;
    }
}
