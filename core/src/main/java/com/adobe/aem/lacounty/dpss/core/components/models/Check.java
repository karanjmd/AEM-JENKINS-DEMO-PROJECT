package com.adobe.aem.lacounty.dpss.core.components.models;

public class Check {
//public static  String urlname="";
	public static void main(String[] args) {
		String urlname = "";
		String pageName ="/content/dpss/en/homepage/sites/faq";
		String[] tokens = pageName.split("/");
		//System.out.println(tokens[tokens.length-1]);
		if(tokens[tokens.length-1].equals("homepage"))
		{
			System.out.println("dpss:en:homepage");
		}
		else
		{
		for (String s1 : tokens) {
			if (!s1.equals("content")) {
				urlname += s1 + ":";
			}
		}
		System.out.println(urlname.substring(1, urlname.length() - 1).toLowerCase().replaceAll("homepage:",""));
		}
		//System.out.println(urlname.substring(1, urlname.length() - 1).toLowerCase());

	}

}
