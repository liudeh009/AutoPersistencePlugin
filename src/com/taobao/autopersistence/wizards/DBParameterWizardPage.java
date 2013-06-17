package com.taobao.autopersistence.wizards;


import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import com.taobao.autopersistence.biz.ConfigDealBiz;
import com.taobao.autopersistence.util.StringUtils;
import com.taobao.autopersistence.vo.ConfigVO;


/**
 * 
 * 类DBParameterWizardPage.java的实现描述：数据库参数设置页面
 * @author zhanzui.ldh 2013-5-30 上午11:51:59
 */
public class DBParameterWizardPage extends WizardPage {
	private ISelection selection;
    private Composite  dbComposite;
    /**数据库类型**/
    private Combo typeCombo;
    /**数据库url**/
    private Text urlText;
    /**数据库用户名**/
    private Text userText;
    /**数据库密码**/
    private Text passwordText;
    private ConfigVO configVO;
    private ConfigDealBiz configBiz;
    private boolean isInit = false;
    private AutoPersistenceWizard autoPersistenceWizard;
    
	public DBParameterWizardPage(ISelection selection,AutoPersistenceWizard autoPersistenceWizard) {
		super("dbwizardPage");
		setTitle("数据库参数设置");
		//setMessage("hello World");
		this.selection = selection;
	    this.autoPersistenceWizard = autoPersistenceWizard;
        this.configVO = autoPersistenceWizard.getConfigVO();
        this.configBiz = autoPersistenceWizard.getConfigBiz();
		this.isInit = true;
	}


	public void createControl(Composite parent) {
	    dbComposite = new Composite(parent,SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        dbComposite.setLayout(gridLayout);
        
        Label typeLabel = new Label(dbComposite, SWT.RIGHT);
        typeLabel.setText("类型:");
        typeCombo = new Combo(dbComposite,SWT.BORDER);
        typeCombo.setItems(new String[]{"mysql","oracle"});
        GridData comboGd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        comboGd.widthHint = 50;
        typeCombo.setLayoutData(comboGd);
        typeCombo.select(0);
   
        Label driverLabel = new Label(dbComposite, SWT.RIGHT);
        driverLabel.setText("url(如ip/dbName):");
        urlText = new Text(dbComposite, SWT.BORDER);  
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.widthHint = 250;
        urlText.setLayoutData(gd);
        urlText.setText("localhost/aliyun_kaoshi");
        
        Label userLabel = new Label(dbComposite, SWT.RIGHT);
        userLabel.setText("用户名:");
        userText = new Text(dbComposite, SWT.BORDER);  
        GridData userGd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        userGd.widthHint = 250;
        userText.setLayoutData(userGd);
        userText.setText("root");
        
        Label passwordLabel = new Label(dbComposite, SWT.CENTER);
        passwordLabel.setText("密码:");
        passwordText = new Text(dbComposite, SWT.BORDER);  
        GridData passwordGd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        passwordGd.widthHint = 250;
        passwordText.setLayoutData(passwordGd);
        passwordText.setText("123456");
        
        setControl(dbComposite);
	}
	 
	private void updateStatus(String message) {
		setErrorMessage(message);
	}


    @Override
    public IWizardPage getNextPage() {
        if(isInit){
            isInit = false;
        }else{
            configVO.setDriver(StringUtils.trim(typeCombo.getText()));
            configVO.setUrl(StringUtils.trim(urlText.getText()));
            configVO.setUserName(StringUtils.trim(userText.getText()));
            configVO.setPassword(StringUtils.trim(passwordText.getText()));
            configVO.setUrl(StringUtils.trim(urlText.getText()));
            isInit = true;
            boolean flag = configBiz.dbParameterValidate(configVO.getDriver(), configVO.getUrl(), configVO.getUserName(), configVO.getPassword());
            if(!flag){
                updateStatus("数据库连接失败,请检查参数");
                setPageComplete(true);
                return super.getWizard().getStartingPage();
            }
        }
        return super.getNextPage();
    }
}