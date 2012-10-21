package org.noranj.tax.v2012.server;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.noranj.core.shared.type.ActivityType;
import org.noranj.idnt.server.service.SystemAdminServiceImpl;
import org.noranj.idnt.shared.dto.AccountDTO;
import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.idnt.shared.dto.UserProfileDTO;
import org.noranj.idnt.shared.type.AccountType;
import org.noranj.tax.v2012.server.service.QuestionServiceImpl;
import org.noranj.tax.v2012.shared.dto.QuestionDTO;
import org.noranj.tax.v2012.shared.dto.TaxServiceDTO;
import org.noranj.tax.v2012.shared.type.QuestionCategoryType;


/**
 * This is the first class that is called at the start up to load the settings and some sample data when needed.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class Startup {

  private static Logger logger = Logger.getLogger(Startup.class.getName());
	
  //TODO it is static and public only for test purpose as it is used in LoginHelper to create some test data. BA-2012-FEB-29 
  /**
   * 
   */
  public static void makeTestDataUserRetailerParty() throws Exception {
    
    String id = null;
    SystemAdminServiceImpl service = new SystemAdminServiceImpl();
    
    //Adding Retailer Party
    AccountDTO account = new AccountDTO(null, "Noranj-Retailer", "http://retailer.noranj.com", ActivityType.Active, AccountType.EndUSer/*account type*/, null /*users*/);
    
    service.addAccount(account);
    
    System.out.printf("Party [%s] is created and its id is [%s]\r\n", account.getName(), account.getId());

    // Adding two users
    UserDTO user = null;
    
    user = new UserDTO(null, "Babak", "Retailer", "babak@noranj.com", account.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO() );
    service.addUser(user); // the id is set when added to data store.
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());

    user = new UserDTO(null, "Buyer", "Retailer", "buyer@noranj.com", account.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO() );
    service.addUser(user);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());
    
  }
  
  //TODO it is static and public only for test purpose as it is used in LoginHelper to create some test data. BA-2012-FEB-29 
  /**
   * 
   */
  public static void makeTestDataUserManufacturerParty() throws Exception {
    
    SystemAdminServiceImpl service = new SystemAdminServiceImpl();
    String id = null;
    
    //Adding Manufacturer Party
    AccountDTO account = new AccountDTO(null/*id*/, "Noranj-Manufacturer", "http://manufacturer.noranj.com", ActivityType.Active, AccountType.EndUSer/*account type*/, null /*users*/);
    service.addAccount(account);
    System.out.printf("Party [%s] is created and its id is [%s]\r\n", account.getName(), account.getId());

    UserDTO user = null;

    user = new UserDTO(null, "Shahab", "Manufacturer", "shahab@noranj.com", account.getId(), ActivityType.Active, System.currentTimeMillis(),System.currentTimeMillis(),new UserProfileDTO());
    service.addUser(user);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());

    user = new UserDTO(null, "Seller", "Manufacturer", "seller@noranj.com", account.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO());
    service.addUser(user);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());
    
  }

  /**
   * Add the questions to data store with a fixed ID.
   * 
   * The actual text and descriptions are localized and stored in message resource bundles.
   * @throws Exception
   */
  public static void addIncomeQuestions() throws Exception {
    
    QuestionServiceImpl service = new QuestionServiceImpl();
    
    QuestionDTO question = null;

    /*
      10-000-00-00  Employment           
      10-000-00-00    Did you work in 2011?         All Services     
      10-000-00-01    Did you receive a T4?   Line 101  All Services  T4   
      10-000-00-02    Did you receive a T4a?    Line 130  Interm, Premier T4A  
    */
    question = new QuestionDTO("10-000-00-00", QuestionCategoryType.Income);
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-001-00-00  Self Employment          
      10-001-00-00    Were you a contractor or self employed? Line 162  Premier T2125  
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-002-00-00  Pension          
      10-002-00-00    Did you receive a pension? Or Retirement type Income?   All Services     
      10-002-00-01      Did you receive a T4A (OAS)? Line 113 All Services  T4A (OAS)  
      10-002-00-02      Did you receive a T4A(P)? Line 114  All Services  T4A (P)  
      10-002-01-00    Were you on EI this year or receive a T4 E? Line 119  All Services  T4 E   
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-003-00-00  RRSP/RRIF Withdraw           
      10-003-00-00    Did you withdrawal Money from RRSP? Line 129  Interm, Invest, Premier, Student  RRSP Worksheet   
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-004-00-00  Investment           
      10-004-00-00    Do you have any Investments?  Line 120  Interm, Invest, Premier Schedule 3   
      10-004-00-01      Saving Account
      10-004-00-02      Dividends
      10-004-00-03      Stock or Bonds Sale
     */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-005-00-00  Rental Property          
      10-005-00-00    Do you have a rental property?  Line 160  Premier T776 
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /* 
      10-006-00-00  Alimony      
      10-006-00-00    Have you received alimony or support payments?         
      10-006-00-01    Did you receive child support payments? Line 156       
      10-006-00-02    Did you receive support payments for yourself? Line 128       
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*             
      10-089-00-00  Other Income           
      10-089-01-00    Did you have any other income?  Line 130  Interm, Invest, Premier Other Income   
      10-089-02-00    Did you have partnership income?      Schedule 4  partnership Income
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
   
  }

  /**
   * Add the questions to data store with a fixed ID.
   * 
   * The actual text and descriptions are localized and stored in message resource bundles.
   * @throws Exception
   */
  public static void addDeductionQuestions() throws Exception {
    
    QuestionServiceImpl service = new QuestionServiceImpl();
    
    QuestionDTO question = null;
    
    /* 
    Deductions
    Question ID*Group*Question*Line Number*Type of Service*Form(s)*Group Name
    */
    
    /*
      20-000-00-00  RRSP  
      20-000-00-00    Have you contributed to an RRSP?        Line 208  Interm, Invest, Premier Schedule 7   
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-001-00-00  Home Buyer  
      20-001-00-00    Are you a first time home buyer this year?  Line 369  Invest, Premier Schedule 1   
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-002-00-00  Education          
      20-002-00-00    Were you a student in 2011?                 Line 323  Student, Interm, Premier  Schedule 11  
      20-002-01-00    Did you have tuition to carry forward from previous years?  Line 323  Student, Interm, Premier  Schedule 11  
      20-002-02-00    Did you pay into any student loans?             Line 319  Student, Interm, Premier  Student Loan Interest  
      20-002-03-00    Have you contributed or withdrawal RRSP for school?     Line 263  Student, Interm, Premier  RRSP Worksheet   
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-003-00-00  Donation  
      20-003-00-00    Have you donated to a charity?                Line 349  All Services  Schedule 9   
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-004-00-00  Moving  
      20-004-00-00    Did you move for school or for work that was more than 40KM?  Line 219  Student, Premier  Moving Expenses  
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-005-00-00  Medical 
      20-005-00-00    Did you have any medical expenses in 2011?            Line 330  Interm, Premier Medical Expenses   
                 */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-006-00-00  Alimony          
      20-006-00-00    Have you paid alimony or support payments?
      20-006-00-01    Did you pay child support?            Line 230       
      20-006-00-02    Did you pay support payments to your spouse?  Line 220       
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*             
      20-007-00-00  TAX Installments           
      20-007-00-00    Did you make any tax installments for 2011? Line 476  All Services   T1?   
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*             
      20-089-00-00  Other deductions           
      20-089-00-00    Did you take transit in 2011?           Line 364  All Services  Transit Worksheet  
      20-089-01-00    Did you have Union or any other Professional Dues?  Line 212  Interm, Premier Union and Professional Dues  
    */
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
   
  }

  /**
   * Add all questions that are displayed for Premier type of service to data store with a fixed ID.
   * 
   * The actual text and descriptions of each question are localized and stored in message resource bundles.
   * @throws Exception
   */
  public static void addPremierQuestions() throws Exception {
    
    QuestionServiceImpl service = new QuestionServiceImpl();
    
    TaxServiceDTO taxService = null;
    List<String> question = null;
    //questionIdsFormattedString 
    /*
      10-000-00-00  Employment           
      10-000-00-00    Did you work in 2011?         All Services     
      10-000-00-01    Did you receive a T4?   Line 101  All Services  T4   
      10-000-00-02    Did you receive a T4a?    Line 130  Interm, Premier T4A  
    */
    question.add("10-000-00-00");
    question.add("10-000-00-01");
    question.add("10-000-00-02");
    //System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-001-00-00  Self Employment          
      10-001-00-00    Were you a contractor or self employed? Line 162  Premier T2125  
    */
    question.add("10-001-00-00");
    //question.add("10-001-00-00");
    //question.add("10-001-00-00");
    //System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-002-00-00  Pension          
      10-002-00-00    Did you receive a pension? Or Retirement type Income?   All Services     
      10-002-00-01      Did you receive a T4A (OAS)? Line 113 All Services  T4A (OAS)  
      10-002-00-02      Did you receive a T4A(P)? Line 114  All Services  T4A (P)  
      10-002-01-00    Were you on EI this year or receive a T4 E? Line 119  All Services  T4 E   
    */
    question.add("10-002-00-00");
    question.add("10-002-00-01");
    question.add("10-002-00-02");

    question.add("10-002-01-00");
    //System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-003-00-00  RRSP/RRIF Withdraw           
      10-003-00-00    Did you withdrawal Money from RRSP? Line 129  Interm, Invest, Premier, Student  RRSP Worksheet   
    */
    question.add("10-003-00-00");
    question.add("10-003-00-01");
    //System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-004-00-00  Investment           
      10-004-00-00    Do you have any Investments?  Line 120  Interm, Invest, Premier Schedule 3   
      10-004-00-01      Saving Account
      10-004-00-02      Dividends
      10-004-00-03      Stock or Bonds Sale
     */
    question.add("10-004-00-00");
    question.add("10-004-00-01");
    question.add("10-004-00-02");
    question.add("10-004-00-03");
    //System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      10-005-00-00  Rental Property          
      10-005-00-00    Do you have a rental property?  Line 160  Premier T776 
    */
    question.add("10-005-00-00");
    question.add("10-005-00-01");
    //System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /* 
      10-006-00-00  Alimony      
      10-006-00-00    Have you received alimony or support payments?         
      10-006-00-01    Did you receive child support payments? Line 156       
      10-006-00-02    Did you receive support payments for yourself? Line 128       
    */
    question.add("10-006-00-00");
    question.add("10-006-00-01");
    question.add("10-006-00-02");
    //System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*             
      10-089-00-00  Other Income           
      10-089-01-00    Did you have any other income?  Line 130  Interm, Invest, Premier Other Income   
      10-089-02-00    Did you have partnership income?      Schedule 4  partnership Income
    */
    question.add("10-089-00-00");
    question.add("10-089-01-00");
    question.add("10-089-02-00");
    //System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
   
    
    /*
      20-000-00-00  RRSP  
      20-000-00-00    Have you contributed to an RRSP?        Line 208  Interm, Invest, Premier Schedule 7   
    *
    question.add("20-089-00-00");
    question.add("20-089-01-00");
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-001-00-00  Home Buyer  
      20-001-00-00    Are you a first time home buyer this year?  Line 369  Invest, Premier Schedule 1   
    *
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-002-00-00  Education          
      20-002-00-00    Were you a student in 2011?                 Line 323  Student, Interm, Premier  Schedule 11  
      20-002-01-00    Did you have tuition to carry forward from previous years?  Line 323  Student, Interm, Premier  Schedule 11  
      20-002-02-00    Did you pay into any student loans?             Line 319  Student, Interm, Premier  Student Loan Interest  
      20-002-03-00    Have you contributed or withdrawal RRSP for school?     Line 263  Student, Interm, Premier  RRSP Worksheet   
    *
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-003-00-00  Donation  
      20-003-00-00    Have you donated to a charity?                Line 349  All Services  Schedule 9   
    *
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-004-00-00  Moving  
      20-004-00-00    Did you move for school or for work that was more than 40KM?  Line 219  Student, Premier  Moving Expenses  
    *
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-005-00-00  Medical 
      20-005-00-00    Did you have any medical expenses in 2011?            Line 330  Interm, Premier Medical Expenses   
                 *
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*
      20-006-00-00  Alimony          
      20-006-00-00    Have you paid alimony or support payments?
      20-006-00-01    Did you pay child support?            Line 230       
      20-006-00-02    Did you pay support payments to your spouse?  Line 220       
    *
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*             
      20-007-00-00  TAX Installments           
      20-007-00-00    Did you make any tax installments for 2011? Line 476  All Services   T1?   
    /
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
    
    /*             
      20-089-00-00  Other deductions           
      20-089-00-00    Did you take transit in 2011?           Line 364  All Services  Transit Worksheet  
      20-089-01-00    Did you have Union or any other Professional Dues?  Line 212  Interm, Premier Union and Professional Dues  
    *
    question = new QuestionDTO();
    service.addQuestion(question);
    System.out.printf("Question [%s] is added and its id is [%s]\r\n", question.getQuestionRKey(), question.getId());
     */
  }

}
