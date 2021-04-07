
/*
 * Author  : Manikanta
 * Description : This class contains all steps and functionality in this test case.
 * Creation Date : 18.12.2020
 * Last modification date : 24.12.2020
 */
package src.com.test;
import src.com.utilities.*;
import java.io.IOException;

import org.testng.annotations.Test;




public class Test_ID_14_Google extends TestBase {

	@Test
	public void RR_140() throws IOException, InterruptedException {
	
		
		logger.info("Google home page is Displayed");
		testPassToReport("Google home page is Displayed");
		String step1 = XLUtils.takeScreenshot_Step("Step1", reportsPathStep);
		returnScreenshotType(step1);
		ScreenshotStepPass("End of Step 1", step1); 
		/*
		base.login();
		testPassToReport("Login success");
		base.waitTime(1);
		hp.AEDButtonIsDisplayed();
		hp.RNRButtonIsDisplayed();
		testPassToReport("AED Button is Displayed");
		testPassToReport("RNR Button is Displayed");
		String step2 = XLUtils.takeScreenshot_Step("Step2", reportsPathStep);
		returnScreenshotType(step2);
		ScreenshotStepPass("End of Step 2", step2);
		
		hp.AEDContentComponentClick();
		base.waitTime(1);
		testPassToReport("AED Button Clicked");
		testPassToReport("AED Content Component Page is Displayed");
		String step3 = XLUtils.takeScreenshot_Step("Step3", reportsPathStep);
		returnScreenshotType(step3);
		ScreenshotStepPass("End of Step 3", step3);
		
		aed.RocheProductBtn_Click();
		testPassToReport("Roche Product Button is Clicked");
		aed.RocheProduct_Perjeta_Checkbox_Click();
		testPassToReport("Pertuzumab (PERJETA) Clicked");
		aed.FilterForm_Click();
		base.waitTime(1);
		String step4 = XLUtils.takeScreenshot_Step("Step4", reportsPathStep);
		returnScreenshotType(step4);
		ScreenshotStepPass("End of Step 4", step4);
		
		aed.Indication_Click();
		testPassToReport("Indication Dropdown Button is Clicked");
		String step5 = XLUtils.takeScreenshot_Step("Step5", reportsPathStep);
		assertText.assertEquals(aed.Checkbox_Label_GetText(), POCGEARConstants.Indication_BC);
		testPassToReport(aed.Checkbox_Label_GetText()+" is Displayed");
		logger.info(aed.Checkbox_Label_GetText()+" is Displayed");
		aed.FilterForm_Click();
		base.waitTime(1);
		returnScreenshotType(step5);
		ScreenshotStepPass("End of Step 5", step5);
		
		aed.MoreFilter_Click();
		aed.LineOfTherapy_Click();
		testPassToReport("Line of Therapy Dropdown Button is Clicked");
		String step6 = XLUtils.takeScreenshot_Step("Step6", reportsPathStep);
		assertText.assertEquals(aed.Checkbox_Label_GetText(), POCGEARConstants.LineOfTherapy_Adjuvant);
		testPassToReport(aed.Checkbox_Label_GetText()+" is Displayed");
		logger.info(aed.Checkbox_Label_GetText()+" is Displayed");
		aed.FilterForm_Click();
		base.waitTime(1);
		returnScreenshotType(step6);
		ScreenshotStepPass("End of Step 6", step6);
		
		aed.Histology_isDisabled();
		testPassToReport("Histology Button is Disabled"); 
		String step7 = XLUtils.takeScreenshot_Step("Step7", reportsPathStep);
		testPassToReport("Histology Button is Disabled");
		returnScreenshotType(step7);
		ScreenshotStepPass("End of Step 7", step7);
		
		aed.DiseasePhase_Click();
		testPassToReport("Disease Phase Dropdown Button is Clicked");
		String step8 = XLUtils.takeScreenshot_Step("Step8", reportsPathStep);
		assertText.assertEquals(aed.Checkbox_Label_GetText(), POCGEARConstants.DiseasePhase_Early);
		testPassToReport(aed.Checkbox_Label_GetText()+" is Displayed");
		logger.info(aed.Checkbox_Label_GetText()+" is Displayed");
		aed.FilterForm_Click();
		base.waitTime(1);
		returnScreenshotType(step8);
		ScreenshotStepPass("End of Step 8", step8);
		
		aed.Biomarker_Click();
		testPassToReport("Biomarker Dropdown Button is Clicked");
		String step9 = XLUtils.takeScreenshot_Step("Step9", reportsPathStep);
		assertText.assertEquals(aed.Checkbox_Label_GetText(), POCGEARConstants.Biomarker_HER2Positive);
		testPassToReport(aed.Checkbox_Label_GetText()+" is Displayed");
		logger.info(aed.Checkbox_Label_GetText()+" is Displayed");
		aed.FilterForm_Click();
		base.waitTime(1);
		returnScreenshotType(step9);
		ScreenshotStepPass("End of Step 9", step9);
		
		aed.StudyNameAndOrNumber_Click();
		testPassToReport("Study Name and/ or Number Dropdown Button is Clicked");
		String step10 = XLUtils.takeScreenshot_Step("Step10", reportsPathStep);
		assertText.assertEquals(aed.Checkbox_Label_GetText(), POCGEARConstants.StudyNameAndOrNumber_Aphinity);
		testPassToReport(aed.Checkbox_Label_GetText()+" is Displayed");
		logger.info(aed.Checkbox_Label_GetText()+" is Displayed");
		aed.FilterForm_Click();
		base.waitTime(1);
		returnScreenshotType(step10);
		ScreenshotStepPass("End of Step 10", step10);
		
		aed.TherapyType_Click();
		testPassToReport("Therapy Type Dropdown Button is Clicked");
		String step11 = XLUtils.takeScreenshot_Step("Step11", reportsPathStep);
		assertText.assertEquals(aed.Checkbox_Label_GetText(), POCGEARConstants.TherapyType_CombinationTherapy);
		testPassToReport(aed.Checkbox_Label_GetText()+" is Displayed");
		logger.info(aed.Checkbox_Label_GetText()+" is Displayed");
		aed.FilterForm_Click();
		base.waitTime(1);
		returnScreenshotType(step11);
		ScreenshotStepPass("End of Step 11", step11);
		
		aed.AEDName_Click();
		testPassToReport("AED Name Dropdown Button is Clicked");
		String step12 = XLUtils.takeScreenshot_Step("Step12", reportsPathStep);
		assertText.assertEquals(aed.Checkbox_Label_GetText(), POCGEARConstants.AEDName_Perjeta_Early_BC_Adjuvant_Aphinity);
		testPassToReport(aed.Checkbox_Label_GetText()+" is Displayed");
		logger.info(aed.Checkbox_Label_GetText()+" is Displayed");
		aed.FilterForm_Click();
		base.waitTime(1);
		returnScreenshotType(step12);
		ScreenshotStepPass("End of Step 12", step12);
		
		aed.Logout_Btn_Click();
		testPassToReport("Logout Button is Clicked");
		base.waitTime(1);
		testPassToReport("Successfully Logout. GEAR Login page is Displayed");
		String step13 = XLUtils.takeScreenshot_Step("Step13", reportsPathStep);
		returnScreenshotType(step13);
		ScreenshotStepPass("End of Step 13", step13);
		*/
		
	}
	
}
