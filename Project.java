import java.io.*;

import java.util.*;

abstract class Project
{
	public static int checkString(String[] a,String[] ssa,int index1)
	{
		int i=index1;
		int j=0;
		while(i<a.length&&j<ssa.length)
		{
			if(a[i].equals(ssa[j]))
			{
				i++;
				j++;
				continue;
			}
			else
			{
				return -1;
			}
		}
		return j;
	}
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();
		//Project p;
		String path = args[0];
		Queue<String> q = new LinkedList<>();
		q.add(path);
		String searchString="Find this String";
		String[] ssa=searchString.split(" ");
		//int i = 100;
		while(!(q.isEmpty()))
		{
			path = q.poll();
			File f = new File(path);
			String[] list = f.list();
			//System.out.println(path);
			if(list == null) continue;
			//System.out.println("number:"+list.length);
			//System.out.println(path);
			for(String s:list)
			{
				//System.out.println(s);
				s = path+"\\"+s;
				//System.out.println(path);
				if(new File(s).isFile())
				{
					//System.out.println("File Found: "+s);
					int lastDot = s.lastIndexOf('.');
//					System.out.println(lastDot+" "+s.length());
					if(lastDot<0) continue;
					if(s.substring(s.lastIndexOf('.')).equals(".txt"))
					{
						//System.out.println("Text File Found: "+s);
						try {
							BufferedReader br = new BufferedReader(new FileReader(s));
							String line = br.readLine();
							while (line != null) {
								int ans;
								//System.out.println(line);
								String[] a = line.split(" ");
								for (int i = 0; i < a.length; i++) {
									if (a[i].equals(ssa[0])) {
										ans = checkString(a, ssa, i);
										if (ans == -1) {
											continue;
										} else if (ans == ssa.length) {
											System.out.println("Required file is: " + s);
										}
									}

								}
								line = br.readLine();
							}
							//System.out.println("****************");
							br.close();
						}
						catch (FileNotFoundException e)
						{
							continue;
						}
					}
				}
				else
				{
					q.add(s);
				}
			}
			//System.out.println(path);
		}
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		System.out.println(elapsedTime);
	}
}