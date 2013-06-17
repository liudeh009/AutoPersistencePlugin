package com.taobao.autopersistence.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import com.taobao.autopersistence.biz.ConfigDealBiz;
import com.taobao.autopersistence.vo.ConfigVO;

/**
 * 
 * 类ProjectParameterWizardPage.java的实现描述：项目参数设置页面
 * @author zhanzui.ldh 2013-5-30 上午11:52:55
 */
public class ProjectParameterWizardPage extends WizardPage {
	private ISelection selection;
	 
    private Composite  pjComposite;
    /**代码作者**/
    private Text authorText;
    /**项目编码**/
    private Combo pjCombo;
    /**代码目录**/
    private Text codeText;
    /**测试代码目录**/
    private Text testText;
    /**资源文件目录**/
    private Text resourceText;
    /**代码包前缀**/
    private Text packageText;
    private ConfigVO configVO;
    private ConfigDealBiz configBiz;
    private boolean isInit = false;
    private AutoPersistenceWizard autoPersistenceWizard;

	public ProjectParameterWizardPage(ISelection selection,AutoPersistenceWizard autoPersistenceWizard) {
		super("pjwizardPage");
		setTitle("项目参数设置");
	    setMessage("\".\"表示当前目录");
		this.selection = selection;
		this.autoPersistenceWizard = autoPersistenceWizard;
	    this.configVO = autoPersistenceWizard.getConfigVO();
	    this.configBiz = autoPersistenceWizard.getConfigBiz();
	    this.isInit = true;
	}

	public void createControl(Composite parent) {
	    pjComposite = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        pjComposite.setLayout(gridLayout);
        
        final Label typeLabel = new Label(pjComposite, SWT.RIGHT);
        typeLabel.setText("开发者:");
        authorText = new Text(pjComposite, SWT.BORDER);  
        GridData authorGd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        authorGd.widthHint = 250;
        authorText.setLayoutData(authorGd);
        authorText.setText("zhanzui.ldh");
        authorText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                textChanged(typeLabel.getText(),authorText.getText());
            }
        });
        
        Label driverLabel = new Label(pjComposite, SWT.RIGHT);
        driverLabel.setText("项目编码:");
        pjCombo = new Combo(pjComposite,SWT.BORDER);
        pjCombo.setItems(new String[]{"GBK","UTF-8"});
        GridData comboGd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        comboGd.widthHint = 50;
        pjCombo.setLayoutData(comboGd);
        pjCombo.select(0);
        pjCombo.setLayoutData(comboGd);
        
        final Label codeLabel = new Label(pjComposite, SWT.RIGHT);
        codeLabel.setText("代码目录:");
        codeText = new Text(pjComposite, SWT.BORDER);  
        GridData codeGd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        codeGd.widthHint = 250;
        codeText.setLayoutData(codeGd);
        codeText.setText("./src/main/java");
        codeText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                textChanged(codeLabel.getText(),codeText.getText());
            }
        });
        
        final Label testLabel = new Label(pjComposite, SWT.CENTER);
        testLabel.setText("测试代码目录:");
        testText = new Text(pjComposite, SWT.BORDER);  
        GridData testTextGd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        testTextGd.widthHint = 250;
        testText.setLayoutData(testTextGd);
        testText.setText("./src/test/java");
        testText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                textChanged(testLabel.getText(),testText.getText());
            }
        });
        
        final Label resourceLabel = new Label(pjComposite, SWT.CENTER);
        resourceLabel.setText("资源文件目录:");
        resourceText = new Text(pjComposite, SWT.BORDER);  
        GridData resourceGd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        resourceGd.widthHint = 250;
        resourceText.setLayoutData(resourceGd);
        resourceText.setText("./src/main/resources");
        resourceText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                textChanged(resourceLabel.getText(),resourceText.getText());
            }
        });
        
        final Label packageLabel = new Label(pjComposite, SWT.CENTER);
        packageLabel.setText("代码包前缀:");
        packageText = new Text(pjComposite, SWT.BORDER);  
        GridData packageGd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        packageGd.widthHint = 250;
        packageText.setLayoutData(packageGd);
        packageText.setText("com.taobao");
        packageText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                textChanged(packageLabel.getText(),packageText.getText());
            }
        });
        
        setControl(pjComposite);
	}
	
	
	private void updateStatus(String message) {
		setErrorMessage(message);
	}

    @Override
    public IWizardPage getPreviousPage() {
        if(isInit){
            isInit = false;
        }else{
            isInit = true;
        }
        autoPersistenceWizard.getdBParameterWizardPage().setErrorMessage(null);
        return super.getPreviousPage();
    }

    private void textChanged(String label, String text){
        if(label != null && !"".equals(label.trim())){
            label = label.substring(0,label.length()-1);
        }
        
        if(text == null || "".equals(text.trim())){
            updateStatus(label + "不能为空");
            setPageComplete(false);
            return;
        }
        updateStatus(null);
        setPageComplete(true);
     }
    public Text getAuthorText() {
        return authorText;
    }

    public Combo getPjCombo() {
        return pjCombo;
    }

    public Text getCodeText() {
        return codeText;
    }

    public Text getTestText() {
        return testText;
    }

    public Text getResourceText() {
        return resourceText;
    }
    
    public Text getPackageText() {
        return packageText;
    }
}