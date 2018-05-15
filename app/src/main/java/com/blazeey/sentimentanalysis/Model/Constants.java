package com.blazeey.sentimentanalysis.Model;

import com.blazeey.sentimentanalysis.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by venki on 24/3/18.
 */

public class Constants {

    public static Map<String,State> statesMap = createStates();
    public static Map<String,State> createStates(){
        Map<String,State> states = new HashMap<>();
        states.put("Andhra Pradesh",new State("Andhra Pradesh",R.drawable.andrapradesh_green,R.drawable.andrapradesh_red,R.drawable.andrapradesh_blue,R.drawable.andrapradesh_grey));
        states.put("Arunachal Pradesh",new State("Arunachal Pradesh",R.drawable.arunachalapradesh_green,R.drawable.arunalachalapradesh_red,R.drawable.arunachalapradesh_blue,R.drawable.arunachalapradesh_grey));
        states.put("Assam",new State("Assam",R.drawable.assam_green,R.drawable.assam_red,R.drawable.assam_blue,R.drawable.assam_grey));
        states.put("Bihar",new State("Bihar",R.drawable.bihar_green,R.drawable.bihar_red,R.drawable.bihar_blue,R.drawable.bihar_grey));
        states.put("Chhattisgarh",new State("Chhattisgarh",R.drawable.chattisgarh_green,R.drawable.chattisgarh_red,R.drawable.chattisgarh_blue,R.drawable.chattisgarh_grey));
        states.put("Delhi",new State("Delhi",R.drawable.delhi_green,R.drawable.delhi_red,R.drawable.delhi_blue,R.drawable.delhi_grey));
        states.put("Goa",new State("Goa",R.drawable.goa_green,R.drawable.goa_red,R.drawable.goa_blue,R.drawable.goa_grey));
        states.put("Gujarat",new State("Gujarat",R.drawable.gujarat_green,R.drawable.gujarat_green,R.drawable.gujarat_blue,R.drawable.gujarat_grey));
        states.put("Haryana",new State("Haryana",R.drawable.haryana_green,R.drawable.haryana_red,R.drawable.haryana_blue,R.drawable.haryana_grey));
        states.put("Himachal Pradesh",new State("Himachal Pradesh",R.drawable.himachalpradesh_green,R.drawable.himachalpradesh_red,R.drawable.himachalpradesh_blue,R.drawable.himachalpradesh_grey));
        states.put("Jammu and Kashmir",new State("Jammu and Kashmir",R.drawable.jammukashmir_green,R.drawable.jammukashmir_red,R.drawable.jammukashmir_blue,R.drawable.jammukashmir_grey));
        states.put("Jharkhand",new State("Jharkhand",R.drawable.jharkhand_green,R.drawable.jharkhand_red,R.drawable.jharkhand_blue,R.drawable.jharkhand_grey));
        states.put("Karnataka",new State("Karnataka",R.drawable.karnataka_green,R.drawable.karnataka_red,R.drawable.karnataka_blue,R.drawable.karnataka_grey));
        states.put("Kerala",new State("Kerala",R.drawable.kerala_green,R.drawable.kerala_red,R.drawable.kerala_blue,R.drawable.kerala_grey));
        states.put("Madhya Pradesh",new State("Madhya Pradesh",R.drawable.madhyapradesh_green,R.drawable.madhapradesh_red,R.drawable.madhyapradesh_blue,R.drawable.madhyapradesh_grey));
        states.put("Maharashtra",new State("Maharashtra",R.drawable.maharastra_green,R.drawable.maharastra_red,R.drawable.maharastra_blue,R.drawable.maharastra_grey));
        states.put("Manipur",new State("Manipur",R.drawable.manipur_green,R.drawable.manipur_red,R.drawable.manipur_blue,R.drawable.manipur_grey));
        states.put("Meghalaya",new State("Meghalaya",R.drawable.meghalaya_green,R.drawable.meghalaya_red,R.drawable.meghalaya_blue,R.drawable.meghalaya_grey));
        states.put("Mizoram",new State("Mizoram",R.drawable.mizoram_green,R.drawable.mizoram_red,R.drawable.mizoram_blue,R.drawable.mizoram_grey));
        states.put("Nagaland",new State("Nagaland",R.drawable.nagaland_green,R.drawable.nagaland_red,R.drawable.nagaland_blue,R.drawable.nagaland_grey));
        states.put("Orissa",new State("Orissa",R.drawable.odisha_green,R.drawable.odisha_red,R.drawable.odisha_blue,R.drawable.odisha_grey));
        states.put("Punjab",new State("Punjab",R.drawable.punjab_green,R.drawable.punjab_red,R.drawable.punjab_blue,R.drawable.punjab_grey));
        states.put("Rajasthan",new State("Rajasthan",R.drawable.rajasthan_green,R.drawable.rajasthan_red,R.drawable.rajasthan_blue,R.drawable.rajasthan_grey));
        states.put("Sikkim",new State("Sikkim",R.drawable.sikkim_green,R.drawable.sikkim_red,R.drawable.sikkim_blue,R.drawable.sikkim_grey));
        states.put("Tamil Nadu",new State("Tamil Nadu", R.drawable.tamilnadu_green,R.drawable.tamilnadu_red,R.drawable.tamilnadu_blue,R.drawable.tamilnadu_grey));
        states.put("Telangana",new State("Telangana",R.drawable.telangana_green,R.drawable.telangana_red,R.drawable.telangana_blue,R.drawable.telangana_grey));
        states.put("Tripura",new State("Tripura",R.drawable.tripura_green,R.drawable.tripura_red,R.drawable.tripura_blue,R.drawable.tripura_grey));
        states.put("Uttarakhand",new State("Uttarakhand",R.drawable.uttarakhand_green,R.drawable.uttatarakhand_red,R.drawable.uttarakhand_blue,R.drawable.uttarakhand_grey));
        states.put("Uttar Pradesh",new State("Uttar Pradesh",R.drawable.uttarpradesh_green,R.drawable.uttarpradesh_red,R.drawable.uttarpradesh_blue,R.drawable.uttarpradesh_grey));
        states.put("West Bengal",new State("West Bengal",R.drawable.westbengal_green,R.drawable.westbengal_red,R.drawable.westbengal_blue,R.drawable.westbengal_grey));

        return states;
    }
}
