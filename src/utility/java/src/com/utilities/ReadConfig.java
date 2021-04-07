package src.com.utilities;
/*
 * This class for find content related Operations
 * 
 * @author Manikanta
 * @version 1.0
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	
	Properties pro;

    private static boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
	
	public ReadConfig()
	{
		File source=new File("./configuration/config.properties");
		try {
			FileInputStream fileinputstream=new FileInputStream(source);
			pro=new Properties();
			pro.load(fileinputstream);
		}
		catch(Exception e){
			System.out.println("Exception is:"+e.getMessage());
			
		}
	}
	
	/* This method is to get property on URL */
	public String getApplicationURL()
	{
		String url=pro.getProperty("baseURL");
		return url;
	}
	
	/* This method is to get property on dataEntry username */
	public String getDataEntryUserName()
	{
		String username=pro.getProperty("dataentry_username");
		return username;
	}
	
	/* This method is to get property on dataEntry username */
	public String getprojectLeadUserName()
	{
		String username=pro.getProperty("projectLead_username");
		return username;
	}
	
	/* This method is to get property on Reviewer username */
	public String getReviewerUserName()
	{
		String reviewer_username=pro.getProperty("reviewer_username");
		return reviewer_username;
	}
	
	/* This method is to get property on Reviewer username */
	public String getApprovarUserName()
	{
		String approvar_username=pro.getProperty("approvar_username");
		return approvar_username;
	}
	
	/* This method is to get property on password */
	public String getPassword()
	{
		String password=pro.getProperty("password");
		return password;
	}	
	
	/* This method is to get property chrome path */
	public String getChromePath()
	{
		String chromepath = isWin ? pro.getProperty("chromepathWindows") : pro.getProperty("chromepath");
       // String chromepath=System.getenv().get("CHROME_DRIVER_PATH");
        if (chromepath == null || chromepath.length() == 0)
            chromepath = pro.getProperty("chromepath");
		return chromepath;
	}

	/* This method is to execute headless */
    public boolean executeHeadless()
    {
        String executeHeadless=System.getenv().get("EXECUTE_HEADLESS");
        if (executeHeadless == null || executeHeadless.length() == 0)
            return true;
        return Boolean.parseBoolean(executeHeadless);
    }
    
    /* This method is to get property on firefox path */
	public String getFirefoxPath()
	{
		String firefoxpath=pro.getProperty("firefoxpath");
		return firefoxpath;
	}
	
	/* This method is to get property on excel download path */
	public String getXlDownloadPath()
	{
		String downloadpath=pro.getProperty("excelfiledownloadpath");
		return downloadpath;
	}
	
	/* This method is to get property on excel filename */
	public String getXlFile()
	{
		String xlfilename=pro.getProperty("xlfilename");
		return xlfilename;
	}
}
