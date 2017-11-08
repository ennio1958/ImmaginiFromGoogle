package com.azserve.imagefromgoogle.source;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;


public class Test1 {
	private static final String FILE_ARTICOLI = "/res/selArticoli.txt";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test1 x = new Test1();
		x.fa();
		

	}

	private void fa() {
		// TODO Auto-generated method stub
		String fileName = System.getProperty("user.dir")+FILE_ARTICOLI;


		Charset cs = Charset.forName("Windows-1252");
		try (Stream<String> stream = Files.lines(Paths.get(fileName), cs)) {

			stream.forEach(articolo->{
				String[] ss = articolo.split("\\|");
				String descrizione = ss[4];
				outputImmaginiGoogle(descrizione);
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void outputImmaginiGoogle(String descrizione) {
		// TODO Auto-generated method stub
		String ricerca = descrizione.replaceAll(" ", "\\+");
		String search = "https://www.google.it/search?biw=1526&bih=708&tbm=isch&sa=1&ei=9DwDWp2LB8TXU5WFuVA&q=" + ricerca + "&oq=" + ricerca+"&gs_l=psy-ab.12..0l8j0i67k1j0.6447.11063.0.28238.21.19.1.0.0.0.245.1790.0j12j1.13.0....0...1.1.64.psy-ab..7.13.1654...0i24k1.0.GfNO_TwlSKs";
		try {
			Document d = Jsoup.connect(search).get();
			Elements rgs = d.select(".rg_meta.notranslate");
			rgs.forEach(rg->{
				JsonElement jelement = new JsonParser().parse(rg.text());
				JsonObject jp = jelement.getAsJsonObject();
				String s = jp.get("ou").getAsString();
				System.out.println(descrizione+"|"+s);
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
