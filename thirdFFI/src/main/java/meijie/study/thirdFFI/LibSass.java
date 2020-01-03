package meijie.study.thirdFFI;

import io.bit3.jsass.Output;
import wrm.libsass.SassCompiler;
import wrm.libsass.SassCompiler.OutputStyle;

public class LibSass {

	public static void test() {

		try {
			
			ClassLoader loader = App.class.getClassLoader();						
			String path = loader.getResource("tests/test.scss").getPath();
			String folder = path.substring(0,  path.lastIndexOf('/'));
			String strInfo = String.format("%s\n%s", path, 
					folder
					//"ss"
					);
			System.out.println(strInfo);
						
			SassCompiler compiler;
			
			compiler = new SassCompiler();
			compiler.setPrecision(5);
			compiler.setOutputStyle(OutputStyle.compressed);
			compiler.setOmitSourceMappingURL(false);
			compiler.setInputSyntax(SassCompiler.InputSyntax.scss);
			compiler.setEmbedSourceMapInCSS(false);
			compiler.setEmbedSourceContentsInSourceMap(false);
			compiler.setGenerateSourceComments(false);
			compiler.setGenerateSourceMap(true);
			compiler.setIncludePaths(null);
			compiler.setEnableClasspathAwareImporter(true);
			
			compiler.setIncludePaths(folder);
			String inPath = folder + "/test.scss",
				   outPath = folder + "/test.css",
				   mapPath = folder + "/test.css.map";
			Output result = compiler.compileFile(inPath, outPath, mapPath);
			System.out.println(result.getCss());
			System.out.println(result.getSourceMap());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
