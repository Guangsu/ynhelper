package cn.edu.qzu.ynhelper;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import cn.edu.qzu.ynhelper.db.DiseaseDBHelper;
import cn.edu.qzu.ynhelper.entity.Disease;

public class DiseaseDetailActivity extends AppCompatActivity {

    private static final String TAG = "DiseaseDetailActivity";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private MenuItem menuStar;
    private FloatingActionButton fab;
    private LinearLayout llSummary;
    private LinearLayout llBasicInfo;
    private LinearLayout llContent;

    private Disease disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        initViews();
        initFab();
        initData();
        adjustViews(disease);
    }

    private void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        llSummary = (LinearLayout) findViewById(R.id.ll_summary);
        llBasicInfo = (LinearLayout) findViewById(R.id.ll_basic_info);
        llContent = (LinearLayout) findViewById(R.id.ll_content);
    }

    private void initFab(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star(view);
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Log.i(TAG,"AppBarLayout===onOffsetChanged");
                if (menuStar != null) menuStar.setVisible(!fab.isShown());
            }
        });
    }

    private void initData(){
        /*JsonObject json = new JsonParser().parse(getTestData()).getAsJsonObject();
        disease = new Gson().fromJson(json.get("data"),Disease.class);*/

        String code = getIntent().getStringExtra("code");
        DiseaseDBHelper dbHelper = new DiseaseDBHelper(this);
        disease = dbHelper.queryDetail(code);
        collapsingToolbarLayout.setBackgroundResource(R.drawable.pggfz);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        menuStar = menu.findItem(R.id.action_star);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_star:
                star(fab);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void star(View view){
        Snackbar.make(view, "收藏成功", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void updateStarState(boolean stared){

    }

    private void adjustViews(Disease disease){
        collapsingToolbarLayout.setTitle(disease.getName());
        if(disease.getSummary() == null
                || disease.getSummary().isEmpty()
                || "null".equals(disease.getSummary().toLowerCase())){
            findViewById(R.id.card_view_summary).setVisibility(View.GONE);
        } else {
            TextView tvSummary = (TextView) findViewById(R.id.tv_summary);
            tvSummary.setText(disease.getSummary());
        }

        if(disease.getCard() == null){
            findViewById(R.id.card_view_basic_info).setVisibility(View.GONE);
        }else {
            List<Map<String,String>> basicInfo = disease.getCard();
            LinearLayout llItem;
            TextView tvKey,tvValue;
            for(Map<String,String> map:basicInfo){
                llItem = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_disease_basic_info,null);
                tvKey = (TextView) llItem.findViewById(R.id.tv_key);
                tvValue = (TextView) llItem.findViewById(R.id.tv_value);
                tvKey.setText(map.get("key"));
                tvValue.setText(map.get("value"));
                llBasicInfo.addView(llItem);
            }
        }
        if(disease.getContent() == null){
            llContent.setVisibility(View.GONE);
        }else {
            List<Map<String,String>> content = disease.getContent();
            CardView cardItem;
            TextView tvKey,tvValue;
            for(Map<String,String> map:content){
                cardItem = (CardView) LayoutInflater.from(this).inflate(R.layout.item_disease_content,null);
                tvKey = (TextView) cardItem.findViewById(R.id.tv_key);
                tvValue = (TextView) cardItem.findViewById(R.id.tv_value);
                tvKey.setText(map.get("key"));
                tvValue.setText(map.get("value"));
                llContent.addView(cardItem);
            }
        }
    }


    private String getTestData(){
        return "{\"data\":{\"card\":[{\"key\":\"中文名\",\"value\":\"草莓灰霉病\"},{\"key\":\"外文名\",\"value\":\" Strawberry Gray Mould\"},{\"key\":\"危害部位\",\"value\":\"花、叶和果实\"},{\"key\":\"类型\",\"value\":\"目前草莓生产中的重要病害\"}],\"code\":\"1341374\",\"content\":[{\"key\":\"症状\",\"value\":\"主要为，也侵害叶片和叶柄。发病多从花期开始，病菌最初从将开败的花或较衰弱的部位侵染，使花呈浅褐色坏死腐烂，产生灰色霉层。叶多从基部老黄叶边缘侵入，形成“V”字形黄褐色斑，或沿花瓣掉落的部位侵染，形成近圆形坏死斑，其上有不甚明显的轮纹，上生较稀疏灰霉。果实染病多从残留的花瓣或靠近或接触地面的部位开始，也可从早期与病残组织接触的部位侵入，初呈水渍状灰褐色坏死，随后颜色变深，果实腐烂，表面产生浓密的灰色霉层。叶柄发病，呈浅褐色坏死、干缩，其上产生稀疏灰霉。\\n\"},{\"key\":\"病原\",\"value\":\"病菌孢子梗数根丛生，褐色，有隔膜，顶端呈1～2次分枝，顶端密生小柄，大小为1452.5～3168.2微米×8.5～11.5微米。分生孢子椭圆形至圆形，单细胞，近无色，大小4.2～10.5微米×3.5～7.5微米。有时产生菌核。\\n\"},{\"key\":\"发病特点\",\"value\":\"病菌以菌丝体、分生孢子随病残体或菌核在土壤内越冬。通过气流、浇水或农事活动传播。温度0～35℃，相对湿度80%以上均可发病，以温度0～25℃、湿度90%以上，或植株表面有积水适宜发病。空气湿度高，或浇水后逢雨天或地势低洼积水等，特别有利此病的发生与发展。另据调查，平畦种植或卧栽盖膜种植病害严重；高垄、地膜栽培病害轻。\\n\"},{\"key\":\"防治方法\",\"value\":\"（1）收获后彻底清除病残落叶\\n移栽或育苗整地前对棚膜、土壤及墙壁等表面喷雾，进行消毒灭菌，并配合喷施新高脂膜增强药效。\\n（2）采用高垄地膜覆盖或滴灌节水栽培\\n选用紫外线阻断膜抑制菌核萌发。开花前期、开花坐果期和浇水前喷药防治，促进果实发育，重点保花保果，协调营养平衡，防治草莓畸形发生，使草莓丰产优质，浇水后加大放风量。使用霉止70---100倍液进行喷雾。（3）发病处理方法一旦发病，应及时小心地将病叶、病花、病果等摘除，放塑料袋内带棚、室外妥善处理。发病后应适当提高管理温度。\\n\"}],\"id\":0,\"name\":\"草莓灰霉病\",\"summary\":\"草莓灰霉病 Strawberry Gray Mould 草莓灰霉病是的害。我国各草莓栽培地区都有发生，20 世纪 70 年代后发病逐渐加重，特别在南方采果期正值春雨时节，发病更为严重。草莓灰霉病的发生 常造成实腐感病品种的病果率在 30%左右，严重的可达 60%以上，对草霉产量、 品质影响很大。\\n\"},\"errCode\":200,\"errMsg\":\"\"}";
    }
}
