package com.practice;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.practice.service.CalculateService;
import com.practice.service.ICalculateService;

/**
 * Unit test for simple App.
 */

public class AppTest
    
{
	private ICalculateService service;
  
	@Before
	public void setUp(){
		service=new CalculateService();
	}
	@Test
	public void calculateDirectorySize() {
		File file=new File("<path of the directory>");
		int size=service.calculateSize(file);
		org.junit.Assert.assertEquals(1878250204, size);
	}
}
