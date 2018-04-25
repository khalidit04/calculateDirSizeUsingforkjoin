package com.practice.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class CalculateService implements ICalculateService {

	public int calculateSize(File file) {
		return sizeOfFork(file);

	}

	public int sizeOfFork(File file) {
		ForkJoinPool pool=new ForkJoinPool();
		try {
		Long result=pool.invoke(new SpawnFork(file));
		}
		finally {
			pool.shutdown();
		}
		return result.intValue();

	}
}

class SpawnFork extends RecursiveTask<Long> {

	private File file;
	public SpawnFork(File file){
		this.file=Objects.requireNonNull(file)
	}
	@Override
	protected Long compute() {
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			List<SpawnFork> tasks=new ArrayList<SpawnFork>();
			for(final File s:children) {
				final SpawnFork sizeOfChild=new SpawnFork(s);
				sizeOfChild.fork();
				tasks.add(sizeOfChild);
			}
			Long size=0l;
			
			for(SpawnFork s:tasks) {
				size=size+s.join();
			}
			return size;
		}else {
			return file.length();
		}
		
	}

}
