package android.leo.electricity.fragment;


import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.activity.LoginActivity;
import android.leo.electricity.activity.home.BindActivity;
import android.leo.electricity.activity.user.ElectricKnowledgeActivity;
import android.leo.electricity.activity.user.ChangePwdValCodeActivity;
import android.leo.electricity.activity.user.ConsultActivity;
import android.leo.electricity.activity.user.SetupActivity;
import android.leo.electricity.activity.user.SuggestActivity;
import android.leo.electricity.activity.user.UnbindActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.leo.electricity.R;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener{
    private ImageView settingButton;
    private ImageView btn_bind;
    private ImageView btn_unbind;
    private ImageView btn_modifyPwd;
    private ImageView btn_eleKnowledge;
    private ImageView btn_userConsult;
    private ImageView btn_userSuggest;

    public UserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        settingButton = (ImageView) view.findViewById(R.id.setting_button);
        btn_bind = (ImageView) view.findViewById(R.id.btn_bind);
        btn_unbind = (ImageView) view.findViewById(R.id.btn_unbind);
        btn_modifyPwd = (ImageView) view.findViewById(R.id.modifyPwd);
        btn_eleKnowledge = (ImageView) view.findViewById(R.id.btn_ele_Knowledge);
        btn_userConsult = (ImageView) view.findViewById(R.id.btn_user_consult);
        btn_userSuggest = (ImageView) view.findViewById(R.id.btn_user_suggest);
        settingButton.setOnClickListener(this);
        btn_bind.setOnClickListener(this);
        btn_unbind.setOnClickListener(this);
        btn_modifyPwd.setOnClickListener(this);
        btn_eleKnowledge.setOnClickListener(this);
        btn_userConsult.setOnClickListener(this);
        btn_userSuggest.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.setting_button:
                intent = new Intent(getActivity(), SetupActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_bind:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), BindActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_unbind:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), UnbindActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.modifyPwd:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), ChangePwdValCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_ele_Knowledge:
                intent = new Intent(getActivity(), ElectricKnowledgeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_user_consult:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), ConsultActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_user_suggest:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), SuggestActivity.class);
                    startActivity(intent);
               }
                break;
        }
    }
}
