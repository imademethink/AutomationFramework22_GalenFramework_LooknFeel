import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.*;


public class GalenExample5_GalenDifferentBrowserSize{

	//	Testing general web element attributes
    String galenSampleUrl 		= "http://samples.galenframework.com/tutorial-corrections/tutorial.html";
	
    @Test
    public void homePageLayoutTest() throws IOException {
    	// testing done at specific browser size which can be customised per user needs
        driver.manage().window().setSize(new Dimension(browserSizeSmallW, browserSizeSmallH));
        
        //Create a layoutReport object
        //checkLayout function checks the layout and returns a LayoutReport object
        LayoutReport objLayoutReport = 
        		Galen.checkLayout(driver, specFilePath, Arrays.asList("GalenDifferentBrowserSize"));
        
        //Create a galen test info list
        List<GalenTestInfo> objGalentestsList	= new LinkedList<GalenTestInfo>();
        //Create a GalenTestInfo object
        GalenTestInfo objSingleGalenTest 		= GalenTestInfo.fromString("GalenDifferentBrowserSize main title here");
        //Get layoutReport and assign to test object
        objSingleGalenTest.getReport().layout(objLayoutReport, "GalenDifferentBrowserSize single test title here");
        //Add test object to the tests list
        objGalentestsList.add(objSingleGalenTest);
        //Create a htmlReportBuilder object
        HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();
        //Create a report under specified folder based on tests list
        htmlReportBuilder.build(objGalentestsList, "ReportFolder_GalenDifferentBrowserSize");
        //If layoutReport has errors Assert Fail
        if (objLayoutReport.errors() > 0){
        	System.out.println("Layout test failed for GalenDifferentBrowserSize");
            Assert.fail();
        }
        System.out.println("Layout test PASSED for GalenDifferentBrowserSize");
    }

    private WebDriver driver = null;
    String specFilePath 			= "galen_spec_gspec/GalenDifferentBrowserSize.gspec";
    int browserSizeLargeW 	= 1600; int browserSizeLargeH 	= 1200;
    int browserSizeMediumW= 1152; int browserSizeMediumH = 864;
    int browserSizeSmallW 	= 800; int browserSizeSmallH 	= 600;

    @Before
    public void setUp() {

        String sChromeBinary=System.getProperty("user.dir") + "\\src\\test\\resources\\browserDriver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", sChromeBinary);
        System.setProperty("webdriver.chrome.silentOutput", "true");

        // Disable image loading - to speedup test execution
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.images", 2);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1400,800");
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.setExperimentalOption("useAutomationExtension", false);

        driver = null;
        driver = new ChromeDriver(options);
        driver.get(galenSampleUrl);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }
}