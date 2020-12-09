import java.io.*;

import java.util.*;
import java.util.concurrent.*; 
import java.util.concurrent.Executors; 

class ProjectSM1
{
    static int count = 0;
    static Stack<String> st = new Stack<>();
    static String[] ssa;
    static  ExecutorService pool = Executors.newFixedThreadPool(100);
    //static String path;
    
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
        String path = args[0];
        //q.add(path);
        
        String searchString="Find this String";
        ssa=searchString.split(" ");
        //int i = 100;
        st.push(path);
        while(true)
        {
            //System.out.print(stackIsEmpty+ "     ");
            //st.peek();
            if(!(st.empty()))
            {
                pool.submit(new MyRunnable(st.pop()));
                //System.out.println(((ThreadPoolExecutor)pool).getActiveCount());
//                if(((ThreadPoolExecutor)pool).getActiveCount()==0&&!MyRunnable.stackIsEmpty)
//                {
//                    System.out.println("IN:54");
//                    break;
//                }
            }
            else if(st.empty()&&((ThreadPoolExecutor)pool).getActiveCount()==0)
            {
                break;
            }
        }
        pool.shutdown();
        System.out.println("FileCount:"+ProjectSM1.count);
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        System.out.println("TiemMs:"+elapsedTime);
    }
}
class MyRunnable implements Runnable
{
    String path;
    MyRunnable(String path)
    {
        this.path = path;
    }
    static String[] ssa = ProjectSM1.ssa;
   //static boolean stackIsEmpty = false;
    public void run()
    {
        /*if(st.empty())
        {
            pool.shutDown();
        }
        else
        {*/
            ProjectSM1.count+=1;
            //System.out.print(ProjectSM1.count+"       ");
            //System.out.print(Thread.currentThread().getName());
            File f = new File(path);
            String[] list = f.list();
            //System.out.println(path);
            
            //System.out.println("number:"+list.length);
            //System.out.println(path);
            try
            {
                for(String s:list)
                {
                    //System.out.println(s);
                    s = path+"\\"+s;
                    //System.out.println(path);
                    if(new File(s).isFile())
                    {
                        //System.out.println("File Found: "+s);
                        int lastDot = s.lastIndexOf('.');
    //                  System.out.println(lastDot+" "+s.length());
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
                                            ans = ProjectSM1.checkString(a, ssa, i);
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
                                //System.out.println(path);
                                continue;
                            }
                            catch (Exception e)
                            {
                                System.out.println(e);
                                continue;
                            }
                        }
                    }
                    else
                    {
                        ProjectSM1.st.push(s);
                        //pool.execute(new MyRunnable(s));
                    }
                }
        }
        catch(NullPointerException e)
        {
            return;
        }
            //System.out.println(path);
        //}
//        if(ProjectSM1.st.empty())
//        {
//            //System.out.println("IN");
//            stackIsEmpty = true;
//        }
    }
}
/* RESULTTTTTTTTTTTT

C:\Aditya\filsearc>java ProjectS C:\(Normal Stack Test1)
Required file is: C:\\Users\adity\Documents\projectsample.txt
Required file is: C:\\test\co.txt
Count:167696
264206

C:\Aditya\filsearc>java ProjectSM1 C:\(MultiThreaded(10) Test1)
Required file is: C:\\test\co.txt
Required file is: C:\\Users\adity\Documents\projectsample.txt
Count:167606
98003

C:\Aditya\filsearc>java ProjectS C:\(Normal Stack Test2)
Required file is: C:\\Users\adity\Documents\projectsample.txt
Required file is: C:\\test\co.txt
FileCount:167696
TimemS:202410

C:\Aditya\filsearc>java ProjectSM1 C:\(MultiThreaded(10) Test2)
Required file is: C:\\test\co.txt
Required file is: C:\\Users\adity\Documents\projectsample.txt
FileCount:167560
TiemMs:93050

C:\Aditya\filsearc>java ProjectSM1 C:\(MultiThreaded(20) Test1)
Required file is: C:\\test\co.txt
Required file is: C:\\Users\adity\Documents\projectsample.txt
FileCount:167556
TiemMs:95250


C:\Aditya\filsearc>java ProjectS C:\ (Normal Stack:05/06/2020:16:54)
C:\\Windows\WinSxS\amd64_microsoft-windows-u..userpredictionmodel_31bf3856ad364e35_10.0.18362.1_none_5f36214c9498167b
C:\\Windows\System32\restore
Required file is: C:\\Users\adity\Documents\projectsample.txt
Required file is: C:\\test\co.txt
FileCount:166341
TimemS:216422

C:\Aditya\filsearc>java ProjectSM1 C:\ (MultiThreaded(10) 05/06/2020:17:01)
Required file is: C:\\test\co.txt
Required file is: C:\\Users\adity\Documents\projectsample.txt
FileCount:166174
TiemMs:98977

*/