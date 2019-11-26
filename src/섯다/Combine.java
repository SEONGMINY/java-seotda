package 섯다;

public class Combine {
	int arr[] = new int[4];
	int jockbo1,jockbo2;
	
	public Combine(){
		for(int i=0;i<4;i++) { //카드 패 값
			arr[i] = (int)(Math.random()*20)+1;
			for(int j=0;j<i;j++) {
				if(arr[j]==arr[i]) {
					i--;
				}
			}
		}
		
		int pro1 = arr[0] > arr[1] ? arr[0]:arr[1]; // 큰수
		int pro2 = arr[2] > arr[3] ? arr[2]:arr[3]; // 큰수
		int pre1 = arr[0] < arr[1] ? arr[0]:arr[1]; // 작은수
		int pre2 = arr[2] < arr[3] ? arr[2]:arr[3]; // 작은수
		
		// 족보 Player1
		if(pre1==5&&pro1==15) {
			jockbo1=100; //38광땡
		} else if(pre1==1&&pro1==5) {
			jockbo1=99; //13광때
		} else if(pre1==1&&pro1==15) {
			jockbo1=98; //18광땡
		} else if(pre1==19&&pro1==20) {
			jockbo1=97; //장땡
		} else if(pre1==17&&pro1==18) {
			jockbo1=96; //9땡
		} else if(pre1==15&&pro1==16) {
			jockbo1=95; //8땡
		} else if(pre1==13&&pro1==14) {
			jockbo1=94; //7땡
		} else if(pre1==11&&pro1==12) {
			jockbo1=93; //6땡
		} else if(pre1==9&&pro1==10) {
			jockbo1=92; //5땡
		} else if(pre1==7&&pro1==8) {
			jockbo1=91; //4땡
		} else if(pre1==5&&pro1==6) {
			jockbo1=90; //3땡
		} else if(pre1==3&&pro1==4) {
			jockbo1=89; //2땡
		} else if(pre1==1&&pro1==2) {
			jockbo1=88; //1땡
		} else if((pre1==1&&pro1==3)||(pre1==1&&pro1==4)||(pre1==2&&pro1==3)||(pre1==2&&pre1==4)) {
			jockbo1=87; //알리
		} else if((pre1==1&&pro1==7)||(pre1==1&&pro1==8)||(pre1==2&&pro1==7)||(pre1==2&&pre1==8)) {
			jockbo1=86; //독사
		} else if((pre1==1&&pro1==17)||(pre1==1&&pro1==18)||(pre1==2&&pro1==17)||(pre1==2&&pre1==18)) {
			jockbo1=85; //구삥
		} else if((pre1==1&&pro1==19)||(pre1==1&&pro1==20)||(pre1==2&&pro1==19)||(pre1==2&&pre1==20)) {
			jockbo1=84; //장삥
		} else if((pre1==7&&pro1==19)||(pre1==7&&pro1==20)||(pre1==8&&pro1==19)||(pre1==8&&pre1==20)) {
			jockbo1=83; //장사
		} else if((pre1==7&&pro1==11)||(pre1==7&&pro1==12)||(pre1==8&&pro1==11)||(pre1==8&&pre1==12)) {
			jockbo1=82; //세륙
		} else if ((pre1==1&&pro1==16)||(pre1==2&&pro1==16)||(pre1==3&&pro1==13)||(pre1==3&&pro1==14)||(pre1==4&&pro1==13)||(pre1==4&&pro1==14)||(pre1==5&&pro1==11)||(pre1==5&&pro1==12)||(pre1==6&&pro1==11)||(pre1==6&&pro1==12)||(pre1==7&&pro1==9)||(pre1==7&&pro1==10)||(pre1==8&&pro1==9)||(pre1==8&&pro1==10)){
			jockbo1=81; //갑오
		} else if((pre1==1&&pro1==13)||(pre1==1&&pro1==14)||(pre1==2&&pro1==13)||(pre1==2&&pro1==14)||(pre1==3&&pro1==11)||(pre1==3&&pro1==12)||(pre1==4&&pro1==11)||(pre1==4&&pro1==12)||(pre1==5&&pro1==9)||(pre1==5&&pro1==10)||(pre1==6&&pro1==9)||(pre1==6&&pro1==10)||(pre1==15&&pro1==19)||(pre1==15&&pro1==20)||(pre1==16&&pro1==19)||(pre1==16&&pro1==20)) {
			jockbo1=80; //8끗
		} else if((pre1==1&&pro1==11)||(pre1==1&&pro1==12)||(pre1==2&&pro1==11)||(pre1==2&&pro1==12)||(pre1==3&&pro1==9)||(pre1==3&&pro1==10)||(pre1==4&&pro1==9)||(pre1==4&&pro1==10)||(pre1==5&&pro1==7)||(pre1==5&&pro1==8)||(pre1==6&&pro1==7)||(pre1==6&&pro1==8)||(pre1==13&&pro1==19)||(pre1==13&&pro1==20)||(pre1==14&&pro1==19)||(pre1==14&&pro1==20)||(pre1==15&&pro1==17)||(pre1==15&&pro1==18)||(pre1==16&&pro1==17)||(pre1==16&&pro1==18)) {
			jockbo1=79; //7끗
		} else if((pre1==1&&pro1==9)||(pre1==1&&pro1==10)||(pre1==2&&pro1==9)||(pre1==2&&pro1==10)||(pre1==3&&pro1==7)||(pre1==3&&pro1==8)||(pre1==4&&pro1==7)||(pre1==4&&pro1==8)||(pre1==11&&pro1==19)||(pre1==11&&pro1==20)||(pre1==12&&pro1==19)||(pre1==12&&pro1==20)||(pre1==15&&pro1==17)||(pre1==15&&pro1==18)||(pre1==16&&pro1==17)||(pre1==16&&pro1==18)) {
			jockbo1=78; //6끗
		} else if((pre1==3&&pro1==5)||(pre1==3&&pro1==6)||(pre1==4&&pro1==5)||(pre1==4&&pro1==6)||(pre1==9&&pro1==19)||(pre1==9&&pro1==20)||(pre1==10&&pro1==19)||(pre1==10&&pro1==20)||(pre1==11&&pro1==17)||(pre1==11&&pro1==18)||(pre1==12&&pro1==17)||(pre1==12&&pro1==18)||(pre1==13&&pro1==15)||(pre1==13&&pro1==16)||(pre1==14&&pro1==15)||(pre1==14&&pro1==16)) {
			jockbo1=77; //5끗
		} else if((pre1==1&&pro1==5)||(pre1==1&&pro1==6)||(pre1==2&&pro1==5)||(pre1==2&&pro1==6)||(pre1==9&&pro1==17)||(pre1==9&&pro1==18)||(pre1==10&&pro1==17)||(pre1==10&&pro1==18)||(pre1==11&&pro1==15)||(pre1==11&&pro1==16)||(pre1==12&&pro1==15)||(pre1==12&&pro1==16))	{
			jockbo1=76; //4끗
		} else if((pre1==5&&pro1==19)||(pre1==5&&pro1==20)||(pre1==6&&pro1==19)||(pre1==6&&pro1==20)||(pre1==9&&pro1==15)||(pre1==9&&pro1==16)||(pre1==10&&pro1==15)||(pre1==10&&pro1==16)||(pre1==11&&pro1==13)||(pre1==11&&pro1==14)||(pre1==12&&pro1==13)||(pre1==12&&pro1==14)) {
			jockbo1=75; //3끗
		} else if((pre1==3&&pro1==19)||(pre1==3&&pro1==20)||(pre1==4&&pro1==19)||(pre1==4&&pro1==20)||(pre1==5&&pro1==17)||(pre1==5&&pro1==18)||(pre1==6&&pro1==17)||(pre1==6&&pro1==18)||(pre1==7&&pro1==15)||(pre1==7&&pro1==16)||(pre1==8&&pro1==15)||(pre1==8&&pro1==16)||(pre1==9&&pro1==13)||(pre1==9&&pro1==14)||(pre1==10&&pro1==13)||(pre1==10&&pro1==14)) {
			jockbo1=74; //2끗
		} else if((pre1==3&&pro1==17)||(pre1==3&&pro1==18)||(pre1==4&&pro1==17)||(pre1==4&&pro1==18)||(pre1==5&&pro1==15)||(pre1==5&&pro1==16)||(pre1==6&&pro1==15)||(pre1==6&&pro1==16)||(pre1==9&&pro1==11)||(pre1==9&&pro1==12)||(pre1==10&&pro1==11)||(pre1==10&&pro1==12)) {
			jockbo1=73; //1끗
		} else if((pre1==1&&pro1==17)||(pre1==1&&pro1==18)||(pre1==2&&pro1==17)||(pre1==2&&pro1==18)||(pre1==3&&pro1==17)||(pre1==3&&pro1==18)||(pre1==4&&pro1==17)||(pre1==4&&pro1==18)||(pre1==5&&pro1==13)||(pre1==5&&pro1==14)||(pre1==6&&pro1==13)||(pre1==6&&pro1==14)||(pre1==7&&pro1==11)||(pre1==7&&pro1==12)||(pre1==8&&pro1==11)||(pre1==8&&pro1==12)) {
			jockbo1=72; //망통
		} else if((pre1==7&&pro1==17)||(pre1==7&&pro1==18)||(pre1==8&&pro1==17)||(pre1==8&&pro1==18)) {
			jockbo1=49; //구사
		} else if((pre1==7&&pro1==13)||(pre1==7&&pro1==14)||(pre1==8&&pro1==13)||(pre1==8&&pro1==14)) {
			jockbo1=47; //암행어사
		} else if((pre1==5&&pro1==13)||(pre1==5&&pro1==14)||(pre1==6&&pro1==13)||(pre1==6&&pro1==14)) {
			jockbo1=37; //땡잡이
		}		
		
		
		// 족보 Player2
		if(pre2==5&&pro2==15) {
			jockbo2=100; //38광땡
		} else if(pre2==1&&pro2==5) {
			jockbo2=99; //13광때
		} else if(pre2==1&&pro2==15) {
			jockbo2=98; //18광땡
		} else if(pre2==19&&pro2==20) {
			jockbo2=97; //장땡
		} else if(pre2==17&&pro2==18) {
			jockbo2=96; //9땡
		} else if(pre2==15&&pro2==16) {
			jockbo2=95; //8땡
		} else if(pre2==13&&pro2==14) {
			jockbo2=94; //7땡
		} else if(pre2==11&&pro2==12) {
			jockbo2=93; //6땡
		} else if(pre2==9&&pro2==10) {
			jockbo2=92; //5땡
		} else if(pre2==7&&pro2==8) {
			jockbo2=91; //4땡
		} else if(pre2==5&&pro2==6) {
			jockbo2=90; //3땡
		} else if(pre2==3&&pro2==4) {
			jockbo2=89; //2땡
		} else if(pre2==1&&pro2==2) {
			jockbo2=88; //1땡
		} else if((pre2==1&&pro2==3)||(pre2==1&&pro2==4)||(pre2==2&&pro2==3)||(pre2==2&&pre2==4)) {
			jockbo2=87; //알리
		} else if((pre2==1&&pro2==7)||(pre2==1&&pro2==8)||(pre2==2&&pro2==7)||(pre2==2&&pre2==8)) {
			jockbo2=86; //독사
		} else if((pre2==1&&pro2==17)||(pre2==1&&pro2==18)||(pre2==2&&pro2==17)||(pre2==2&&pre2==18)) {
			jockbo2=85; //구삥
		} else if((pre2==1&&pro2==19)||(pre2==1&&pro2==20)||(pre2==2&&pro2==19)||(pre2==2&&pre2==20)) {
			jockbo2=84; //장삥
		} else if((pre2==7&&pro2==19)||(pre2==7&&pro2==20)||(pre2==8&&pro2==19)||(pre2==8&&pre2==20)) {
			jockbo2=83; //장사
		} else if((pre2==7&&pro2==11)||(pre2==7&&pro2==12)||(pre2==8&&pro2==11)||(pre2==8&&pre2==12)) {
			jockbo2=82; //세륙
		} else if ((pre2==1&&pro2==16)||(pre2==2&&pro2==16)||(pre2==3&&pro2==13)||(pre2==3&&pro2==14)||(pre2==4&&pro2==13)||(pre2==4&&pro2==14)||(pre2==5&&pro2==11)||(pre2==5&&pro2==12)||(pre2==6&&pro2==11)||(pre2==6&&pro2==12)||(pre2==7&&pro2==9)||(pre2==7&&pro2==10)||(pre2==8&&pro2==9)||(pre2==8&&pro2==10)){
			jockbo2=81; //갑오
		} else if((pre2==1&&pro2==13)||(pre2==1&&pro2==14)||(pre2==2&&pro2==13)||(pre2==2&&pro2==14)||(pre2==3&&pro2==11)||(pre2==3&&pro2==12)||(pre2==4&&pro2==11)||(pre2==4&&pro2==12)||(pre2==5&&pro2==9)||(pre2==5&&pro2==10)||(pre2==6&&pro2==9)||(pre2==6&&pro2==10)||(pre2==15&&pro2==19)||(pre2==15&&pro2==20)||(pre2==16&&pro2==19)||(pre2==16&&pro2==20)) {
			jockbo2=80; //8끗
		} else if((pre2==1&&pro2==11)||(pre2==1&&pro2==12)||(pre2==2&&pro2==11)||(pre2==2&&pro2==12)||(pre2==3&&pro2==9)||(pre2==3&&pro2==10)||(pre2==4&&pro2==9)||(pre2==4&&pro2==10)||(pre2==5&&pro2==7)||(pre2==5&&pro2==8)||(pre2==6&&pro2==7)||(pre2==6&&pro2==8)||(pre2==13&&pro2==19)||(pre2==13&&pro2==20)||(pre2==14&&pro2==19)||(pre2==14&&pro2==20)||(pre2==15&&pro2==17)||(pre2==15&&pro2==18)||(pre2==16&&pro2==17)||(pre2==16&&pro2==18)) {
			jockbo2=79; //7끗
		} else if((pre2==1&&pro2==9)||(pre2==1&&pro2==10)||(pre2==2&&pro2==9)||(pre2==2&&pro2==10)||(pre2==3&&pro2==7)||(pre2==3&&pro2==8)||(pre2==4&&pro2==7)||(pre2==4&&pro2==8)||(pre2==11&&pro2==19)||(pre2==11&&pro2==20)||(pre2==12&&pro2==19)||(pre2==12&&pro2==20)||(pre2==15&&pro2==17)||(pre2==15&&pro2==18)||(pre2==16&&pro2==17)||(pre2==16&&pro2==18)) {
			jockbo2=78; //6끗
		} else if((pre2==3&&pro2==5)||(pre2==3&&pro2==6)||(pre2==4&&pro2==5)||(pre2==4&&pro2==6)||(pre2==9&&pro2==19)||(pre2==9&&pro2==20)||(pre2==10&&pro2==19)||(pre2==10&&pro2==20)||(pre2==11&&pro2==17)||(pre2==11&&pro2==18)||(pre2==12&&pro2==17)||(pre2==12&&pro2==18)||(pre2==13&&pro2==15)||(pre2==13&&pro2==16)||(pre2==14&&pro2==15)||(pre2==14&&pro2==16)) {
			jockbo2=77; //5끗
		} else if((pre2==1&&pro2==5)||(pre2==1&&pro2==6)||(pre2==2&&pro2==5)||(pre2==2&&pro2==6)||(pre2==9&&pro2==17)||(pre2==9&&pro2==18)||(pre2==10&&pro2==17)||(pre2==10&&pro2==18)||(pre2==11&&pro2==15)||(pre2==11&&pro2==16)||(pre2==12&&pro2==15)||(pre2==12&&pro2==16))	{
			jockbo2=76; //4끗
		} else if((pre2==5&&pro2==19)||(pre2==5&&pro2==20)||(pre2==6&&pro2==19)||(pre2==6&&pro2==20)||(pre2==9&&pro2==15)||(pre2==9&&pro2==16)||(pre2==10&&pro2==15)||(pre2==10&&pro2==16)||(pre2==11&&pro2==13)||(pre2==11&&pro2==14)||(pre2==12&&pro2==13)||(pre2==12&&pro2==14)) {
			jockbo2=75; //3끗
		} else if((pre2==3&&pro2==19)||(pre2==3&&pro2==20)||(pre2==4&&pro2==19)||(pre2==4&&pro2==20)||(pre2==5&&pro2==17)||(pre2==5&&pro2==18)||(pre2==6&&pro2==17)||(pre2==6&&pro2==18)||(pre2==7&&pro2==15)||(pre2==7&&pro2==16)||(pre2==8&&pro2==15)||(pre2==8&&pro2==16)||(pre2==9&&pro2==13)||(pre2==9&&pro2==14)||(pre2==10&&pro2==13)||(pre2==10&&pro2==14)) {
			jockbo2=74; //2끗
		} else if((pre2==3&&pro2==17)||(pre2==3&&pro2==18)||(pre2==4&&pro2==17)||(pre2==4&&pro2==18)||(pre2==5&&pro2==15)||(pre2==5&&pro2==16)||(pre2==6&&pro2==15)||(pre2==6&&pro2==16)||(pre2==9&&pro2==11)||(pre2==9&&pro2==12)||(pre2==10&&pro2==11)||(pre2==10&&pro2==12)) {
			jockbo2=73; //1끗
		} else if((pre2==1&&pro2==17)||(pre2==1&&pro2==18)||(pre2==2&&pro2==17)||(pre2==2&&pro2==18)||(pre2==3&&pro2==17)||(pre2==3&&pro2==18)||(pre2==4&&pro2==17)||(pre2==4&&pro2==18)||(pre2==5&&pro2==13)||(pre2==5&&pro2==14)||(pre2==6&&pro2==13)||(pre2==6&&pro2==14)||(pre2==7&&pro2==11)||(pre2==7&&pro2==12)||(pre2==8&&pro2==11)||(pre2==8&&pro2==12)) {
			jockbo2=72; //망통
		} else if((pre2==7&&pro2==17)||(pre2==7&&pro2==18)||(pre2==8&&pro2==17)||(pre2==8&&pro2==18)) {
			jockbo2=49; //구사
		} else if((pre2==7&&pro2==13)||(pre2==7&&pro2==14)||(pre2==8&&pro2==13)||(pre2==8&&pro2==14)) {
			jockbo2=47; //암행어사
		} else if((pre2==5&&pro2==13)||(pre2==5&&pro2==14)||(pre2==6&&pro2==13)||(pre2==6&&pro2==14)) {
			jockbo2=37; //땡잡이
		}
	}
	
}