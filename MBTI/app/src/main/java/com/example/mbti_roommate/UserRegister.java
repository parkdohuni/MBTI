package com.example.mbti_roommate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


//회원가입 클래스

public class UserRegister extends AppCompatActivity {

    private EditText id_EditText;
    private EditText pw_EditText;
    private EditText pwCheck_EditText;
    private EditText name_EditText;
    private Spinner age_spinner;
    private Spinner univ_spinner;
    private Spinner pmajor_spinner;
    private Spinner dorm_spinner;
    private EditText email_EditText;
    private Spinner email_spinner;
    //private EditText pcontact_EditText;
    private Spinner mbti_spinner;
    private Spinner PMAM_spinner;
    private Spinner psTime_spinner;
    private Spinner psHour_spinner;
    private EditText comment_EditText;

    private Button signup_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mbti_spinner = findViewById(R.id.MBTI_spinner);
        age_spinner = findViewById(R.id.age_spinner);
        email_spinner = findViewById(R.id.email_spinner);
        dorm_spinner = findViewById(R.id.dorm_spinner);
        univ_spinner = findViewById(R.id.univ_spinner);
        PMAM_spinner = findViewById(R.id.PMAM_spinner);
        psTime_spinner = findViewById(R.id.psTime_spinner);
        psHour_spinner = findViewById(R.id.psHour_spinner);
        signup_button = findViewById(R.id.signup_button);
        pmajor_spinner = findViewById(R.id.major_spinner);

        ArrayAdapter mbtiAdapter = ArrayAdapter.createFromResource
                (this, R.array.mbti_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter ageAdapter = ArrayAdapter.createFromResource
                (this, R.array.age_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter univAdapter = ArrayAdapter.createFromResource
                (this, R.array.univ_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter dormAdapter = ArrayAdapter.createFromResource
                (this, R.array.dorm_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter emailAdapter = ArrayAdapter.createFromResource
                (this, R.array.email_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter PMAMAdapter = ArrayAdapter.createFromResource
                (this, R.array.PMAM_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter psTimeAdapter = ArrayAdapter.createFromResource
                (this, R.array.pstime_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter psHourAdapter = ArrayAdapter.createFromResource
                (this, R.array.pshour_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter pmajorAdapter = ArrayAdapter.createFromResource(this, R.array.pmajor_array, android.R.layout.simple_spinner_dropdown_item);

        mbtiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mbti_spinner.setAdapter(mbtiAdapter);

        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_spinner.setAdapter(ageAdapter);

        emailAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        email_spinner.setAdapter(emailAdapter);

        dormAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dorm_spinner.setAdapter(dormAdapter);

        univAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        univ_spinner.setAdapter(univAdapter);

        PMAMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PMAM_spinner.setAdapter(PMAMAdapter);

        psTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psTime_spinner.setAdapter(psTimeAdapter);

        psHourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psHour_spinner.setAdapter(psHourAdapter);

        pmajorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pmajor_spinner.setAdapter(pmajorAdapter);



        id_EditText = (EditText) findViewById(R.id.userID_editText);
        pw_EditText = (EditText) findViewById(R.id.userPW_editText);
        pwCheck_EditText = (EditText) findViewById(R.id.userPWCheck_editText);
        name_EditText = (EditText) findViewById(R.id.userName_editText);
        final RadioGroup gender_RadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        final RadioGroup isSmoker_RadioGroup = (RadioGroup) findViewById(R.id.isSmokerRadioGroup);
        email_EditText = (EditText)findViewById(R.id.email_editText);
        comment_EditText = (EditText) findViewById(R.id.pcomment);
        //contact_EditText = (EditText)findViewById(R.id.user_contact);

        //회원가입 버튼 누르기
        signup_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("here come in!","click begin");

                //아이디
                String id = id_EditText.getText().toString();

                //비밀번호
                String password = pw_EditText.getText().toString();

                //비밀번호 확인
                String pwCheck= pwCheck_EditText.getText().toString();

                //이름
                String pname = name_EditText.getText().toString();

                //성별
                int pgenderInt = 2;         //초기화, 2는 에러값
                RadioButton gender_RadioButton = (RadioButton) findViewById(gender_RadioGroup.getCheckedRadioButtonId());
                try{
                    if ((gender_RadioButton.getText().toString()).equals("남자"))
                        pgenderInt = 0;
                    else if ((gender_RadioButton.getText().toString().equals("여자")))
                        pgenderInt = 1;
                }catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"성별을 선택해 주세요.",Toast.LENGTH_LONG).show();
                }
                String pgender = Integer.toString(pgenderInt);

                //mbti
                //순서는 @values/array.xml
                int pmbtiInt = 0;           //초기화
                if ((mbti_spinner.getSelectedItem().toString()).equals("INTJ"))
                    pmbtiInt = 0;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("INTP"))
                    pmbtiInt = 1;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ENTJ"))
                    pmbtiInt = 2;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ENTP"))
                    pmbtiInt = 3;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("INFJ"))
                    pmbtiInt = 4;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("INFP"))
                    pmbtiInt = 5;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ENFJ"))
                    pmbtiInt = 6;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ENFP"))
                    pmbtiInt = 7;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ISTJ"))
                    pmbtiInt = 8;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ISTP"))
                    pmbtiInt = 9;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ESTJ"))
                    pmbtiInt = 10;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ESTP"))
                    pmbtiInt = 11;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ISFJ"))
                    pmbtiInt = 12;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ISFP"))
                    pmbtiInt = 13;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ESFJ"))
                    pmbtiInt = 14;
                else if ((mbti_spinner.getSelectedItem().toString()).equals("ESFP"))
                    pmbtiInt = 15;
                String pmbti = Integer.toString(pmbtiInt);

                //대학교
                int univInt = 1111;     //초기화
                if ((univ_spinner.getSelectedItem().toString()).equals("경북대학교(대구캠)"))
                    univInt = 1111;
                else if ((univ_spinner.getSelectedItem().toString()).equals("경북대학교(상주캠)"))
                    univInt = 1112;
                String univ = Integer.toString(univInt);

                //학과
                int pmajorInt = 11110001;   //초기화
                if (pmajor_spinner.getSelectedItem().equals("컴퓨터학부"))
                    pmajorInt = 11110001;
                else if (pmajor_spinner.getSelectedItem().equals("전자공학부"))
                    pmajorInt = 11110005;
                else if (pmajor_spinner.getSelectedItem().equals("경영학부"))
                    pmajorInt = 11110002;
                else if (pmajor_spinner.getSelectedItem().equals("영어영문학"))
                    pmajorInt = 11110003;
                else if (pmajor_spinner.getSelectedItem().equals("수학과"))
                    pmajorInt = 11110004;


                //기숙사
                int pdormitoryInt = 11110001;   //초기화
                if ((dorm_spinner.getSelectedItem().toString()).equals("첨성관"))
                    pdormitoryInt = 11110001;
                else if((dorm_spinner.getSelectedItem().toString()).equals("성실관"))
                    pdormitoryInt = 11110002;
                else if((dorm_spinner.getSelectedItem().toString()).equals("긍지관"))
                    pdormitoryInt = 11110003;
                else if((dorm_spinner.getSelectedItem().toString()).equals("협동관"))
                    pdormitoryInt = 11110004;
                else if((dorm_spinner.getSelectedItem().toString()).equals("봉사관"))
                    pdormitoryInt = 11110005;
                else if((dorm_spinner.getSelectedItem().toString()).equals("진리관"))
                    pdormitoryInt = 11110006;
                else if((dorm_spinner.getSelectedItem().toString()).equals("화목관"))
                    pdormitoryInt = 11110007;
                else if((dorm_spinner.getSelectedItem().toString()).equals("면학관"))
                    pdormitoryInt = 11110008;
                else if((dorm_spinner.getSelectedItem().toString()).equals("향토관"))
                    pdormitoryInt = 11110009;
                else if((dorm_spinner.getSelectedItem().toString()).equals("명의관"))
                    pdormitoryInt = 11110010;
                else if((dorm_spinner.getSelectedItem().toString()).equals("누리관"))
                    pdormitoryInt = 11110011;
                String pdormitory = Integer.toString(pdormitoryInt);

                //연령
                String page = age_spinner.getSelectedItem().toString();

                //이메일
                String emailID = email_EditText.getText().toString();
                String emailAddress = email_spinner.getSelectedItem().toString();
                String email = emailID + "@" + emailAddress;
                String pmajor = String.valueOf(pmajorInt);
                //연락처
                String pcontact = email;
                //String pcontact = pcontact_EditText.getText().toString;

                //흡연 유무
                int psmokeBool = 2;         //초기화, 2 = error값
                RadioButton psmoke_RadioButton = (RadioButton) findViewById(isSmoker_RadioGroup.getCheckedRadioButtonId());
                try {
                    if ((psmoke_RadioButton.getText().toString()).equals("예"))
                        psmokeBool = 1;
                    else if ((psmoke_RadioButton.getText().toString().equals("아니오")))
                        psmokeBool = 0;
                } catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"흡연 유무를 선택해 주세요.",Toast.LENGTH_LONG).show();
                }
                String psmoke = String.valueOf(psmokeBool);

                //수면 시간, 수면 시각
                int pmam = 0;
                if ((PMAM_spinner.getSelectedItem().toString()).equals("PM"))
                    pmam = 12;
                String pstime = Integer.toString((pmam + Integer.parseInt(psTime_spinner.getSelectedItem().toString())));
                String pshour = psHour_spinner.getSelectedItem().toString();

                //자기에 대한 상세 설명
                String pcomment = comment_EditText.getText().toString();

//                Log.e("ID", id);
//                Log.e("PW", password);
//                Log.e("PWCHECK", pwCheck);
//                Log.e("NAME", pname);
//                Log.e("EMAILID", emailID);
//                Log.e("CONTACT", pcontact);

                if (RegisterExceptionHandler(id, password, pwCheck, pname, pgender, emailID, psmoke, pcontact)){
                    sendRequest(id, password, pname, pgender, pmbti, pdormitory, univ, pmajor, email, psmoke, pcomment, page, pcontact, pstime, pshour);
                }
            }
        });
    }

    //sendRequest Trigger
    public Boolean RegisterExceptionHandler(final String id, final String password, final String pwCheck, final String pname,
                                            final String pgender,  final String emailID, final String psmoke, final String pcontact){
        Log.e("REH","begin");
        Boolean result = true;
        if(id.equals("")){
            Log.e("Error","ID Error");
            Toast.makeText(getApplicationContext(),"아이디를 입력해 주세요.",Toast.LENGTH_LONG).show();
            result = false;
        }
        if(password.equals("")){
            Log.e("Error","PW Error");
            Toast.makeText(getApplicationContext(),"비밀번호를 입력해 주세요.",Toast.LENGTH_LONG).show();
            result = false;
        }
        if(pwCheck.equals("")){
            Log.e("Error","PWCheck Error");
            Toast.makeText(getApplicationContext(),"비밀번호 확인을 입력해 주세요.",Toast.LENGTH_LONG).show();
            result = false;
        }
        if(!pwCheck.equals(password)){
            Log.e("Error","PW And PWCheck not equal Error");
            Toast.makeText(getApplicationContext(), "비밀번호와 비밀번호 확인이 서로 다릅니다.", Toast.LENGTH_LONG).show();
            result = false;
        }
        if(pname.equals("")){
            Log.e("Error","Name Error");
            Toast.makeText(getApplicationContext(),"이름를 입력해 주세요.",Toast.LENGTH_LONG).show();
            result = false;
        }
        if(pgender.equals("2")){
            Log.e("Error","PGender Error");
            result = false;
        }
        if(emailID.equals("")){
            Log.e("Error","EmailID Error");
            Toast.makeText(getApplicationContext(),"이메일 아이디를 입력해 주세요.",Toast.LENGTH_LONG).show();
            result = false;
        }
        if(pcontact.equals("")){
            Log.e("Error","PContact Error");
            Toast.makeText(getApplicationContext(),"연락처를 입력해 주세요.",Toast.LENGTH_LONG).show();
            result = false;
        }
        if(psmoke.equals("2")){
            Log.e("Error","PSmoke Error");
            result = false;
        }
        Log.e("REH",result.toString());
        return result;
    }

    public void openMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void sendRequest(final String id, final String password, final String pname, final String pgender, final String pmbti, final String pdormitory, final String univ,final String pmajor, final String email,
                            final String psmoke, final String pcomment, final String page, final String pcontact, final String pstime, final String pshour){
        RequestQueue requestQueue = Volley.newRequestQueue(UserRegister.this);  //이 에러가 도대체 뭘까요??????????????????
        String url = urlManager.registerURL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                     try {
                         JSONObject jsonObj = new JSONObject(response);
                         boolean isSuccessed = jsonObj.getBoolean("success");
                         Log.e("Success",String.valueOf(isSuccessed));
                         if(isSuccessed) {
                             openMainPage();
                         }
                         else{
                             Toast info = Toast.makeText(getApplicationContext(),jsonObj.getString("reason"),Toast.LENGTH_LONG);
                             info.show();
                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id",id);
                params.put("password",password);
                params.put("pname", pname);
                params.put("pgender", pgender);
                params.put("pmbti", pmbti);
                params.put("pdormitory", pdormitory);
                params.put("univ", univ);
                params.put("pmajor",pmajor);
                params.put("email", email);
                params.put("psmoke", psmoke);
                params.put("pcomment", pcomment);
                params.put("page", page);
                params.put("pcontact", pcontact);
                params.put("pstime", pstime);
                params.put("pshour", pshour);
                return params;
            }
            @Override
            public  Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Context-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
