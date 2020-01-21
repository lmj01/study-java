package meijie.study.xuggle;

import org.bytedeco.javacpp.avcodec;

public class TestJavaCpp {
	
	public static void test() {
		System.out.println("测试javaCPP的FFmpeg的环境是否成功---， 成功会编译时的配置信息！");
		String str = avcodec.avcodec_configuration().getString();
		System.out.println(str);
	}
}
