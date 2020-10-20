package com.dotcom.testcases;

import org.testng.annotations.Test;
import org.testng.annotations.Test;

import com.dotcom.keyword.engine.KeyWordEngine;

public class AddInternetToCart {

	
public KeyWordEngine NewKeyWordEngine;
	
	@Test (groups = "sanity" )
	
	public void AddInternetTOCart() throws Throwable
	
	{
		NewKeyWordEngine=new KeyWordEngine();
		NewKeyWordEngine.startExecution("TestSteps");
	}

}
