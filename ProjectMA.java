import java.io.*;

import java.util.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.*;
import java.util.concurrent.Executors;

class ProjectMA
{
    static int count = 0;
    static int folderCount = 0;
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
        //st.push(path);
        pool.submit(new MyRunnable(path));
        folderCount+=1;
        //Thread.currentThread().setPriority(3);
        while(true)
        {
            /*try
            {
                //Thread.sleep(500);
            }
            catch(InterruptedException e)
            {

            }*/
            //System.out.println(folderCount);
            if(folderCount<=0)
            {
                //System.out.println(((ThreadPoolExecutor)pool).getActiveCount());
                pool.shutdown();
                break;
            }
        }
        /*while(true)
        {
            //System.out.print(stackIsEmpty+ "     ");
            //st.peek();
            if(!(st.empty()))
            {
                pool.submit(new MyRunnable(st.pop()));
                //System.out.println(((ThreadPoolExecutor)pool).getActiveCount());
                if(((ThreadPoolExecutor)pool).getActiveCount()==0&&!MyRunnable.stackIsEmpty)
                {
                    break;
                }
            }
        }*/
        //pool.shutdown();
        System.out.println(count);
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        System.out.println("TotalFiles:"+count);
        System.out.println("TimeMS:"+elapsedTime);
    }
}
class MyRunnable implements Runnable
{
    static ReentrantLock r = new ReentrantLock();
    String path;
    MyRunnable(String path)
    {
        this.path = path;
    }
    static String[] ssa = ProjectMA.ssa;
    static boolean stackIsEmpty = false;
    public void run()
    {
        /*if(st.empty())
        {
            pool.shutDown();
        }
        else
        {*/
        ProjectMA.count+=1;
        //System.out.print(ProjectMA.count+"       ");
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
                                        ans = ProjectMA.checkString(a, ssa, i);
                                        if (ans == -1) {
                                            continue;
                                        } else if (ans == ssa.length) {
                                            System.out.println("Required file is: " + s);
                                        }
                                    }

                                }
                                line = br.readLine();
                            }
                            //System.out.println("******");
                            br.close();
                        }
                        catch (Exception e)
                        {
                            continue;
                        }
                    }
                }
                else
                {
                    //ProjectMA.st.push(s);
                    ProjectMA.pool.submit(new MyRunnable(s));
                    r.lock();
                    ProjectMA.folderCount+=1;
                    r.unlock();
                }
            }
        }
        catch(NullPointerException e)
        {

        }
        r.lock();
        //System.out.println(ProjectMA.folderCount);
        ProjectMA.folderCount-=1;
        r.unlock();
        //System.out.println(path);
        //}
        /*if(ProjectMA.st.empty())
        {
            stackIsEmpty = true;
        }*/
    }
}