#include<statistika.h>

JNIEXPORT jint JNICALL Java_rtrk_pnrs1_ra43_12014_MyNative_racun(JNIEnv *env, jobject obj, jint ukupanBr, jint odradjeniBr)
{
	jint procenat = 0;
	
	if(ukupanBr == 0)
		return 0;
	
	procenat = (odradjeniBr * 100) / ukupanBr;
	return procenat;
}