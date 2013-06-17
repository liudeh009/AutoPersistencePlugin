package com.taobao.autopersistence.wizards;


import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.taobao.autopersistence.biz.ConfigDealBiz;
import com.taobao.autopersistence.util.StringUtils;
import com.taobao.autopersistence.vo.ConfigVO;

/**
 * 
 * 类AutoPersistenceWizard.java的实现描述：参数向导主类
 * @author zhanzui.ldh 2013-5-30 上午11:52:14
 */
public class AutoPersistenceWizard extends Wizard implements INewWizard {
	private ISelection selection;
	private ConfigVO configVO;
	private ConfigDealBiz configBiz;
	private DBParameterWizardPage dBParameterWizardPage;
	private ProjectParameterWizardPage pjParameterWizardPage;
	
	public AutoPersistenceWizard(String pjPath) {
		super();
		setNeedsProgressMonitor(true);
	    configVO = new ConfigVO(pjPath);
        configBiz = new ConfigDealBiz();
	}
	
    public void init(IWorkbench workbench, IStructuredSelection selection) {
	        this.selection = selection;
	}
	   
	public void addPages() {
	    dBParameterWizardPage = new DBParameterWizardPage(selection,this);
		addPage(dBParameterWizardPage);
		
		pjParameterWizardPage = new ProjectParameterWizardPage(selection,this);
	    addPage(pjParameterWizardPage);
	}
    
	@Override
    public boolean canFinish() {
	    if (pjParameterWizardPage.equals(this.getContainer().getCurrentPage()))
	        if(pjParameterWizardPage.isPageComplete()){
	            return true;
	        }
	     return false;
    }
	
    /**
     * 完成按钮被按下
     */
	public boolean performFinish() {
	    configVO.setAuthor(StringUtils.trim(pjParameterWizardPage.getAuthorText().getText()));
        configVO.setCharset(StringUtils.trim(pjParameterWizardPage.getPjCombo().getText()));
        configVO.setJavadir(StringUtils.trim(pjParameterWizardPage.getCodeText().getText()));
        configVO.setJavaTestDir(StringUtils.trim(pjParameterWizardPage.getTestText().getText()));
        configVO.setResource(StringUtils.trim(pjParameterWizardPage.getResourceText().getText()));
        configVO.setPjpackage(StringUtils.trim(pjParameterWizardPage.getPackageText().getText()));
       
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor) throws InvocationTargetException {
                try {
                    configBiz.deal(configVO,monitor);
                } catch (Exception e) {
                    throw new InvocationTargetException(e);
                } finally {
                    monitor.done();
                }
            }
        };
        try {
            getContainer().run(true, false, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            Throwable realException = e.getTargetException();
            MessageDialog.openError(getShell(), "Error", realException.getMessage());
            return false;
        }
       return true;
	}
	
    public DBParameterWizardPage getdBParameterWizardPage() {
        return dBParameterWizardPage;
    }

    public ProjectParameterWizardPage getPjParameterWizardPage() {
        return pjParameterWizardPage;
    }
    
    public ConfigVO getConfigVO() {
        return configVO;
    }
    
    public ConfigDealBiz getConfigBiz() {
        return configBiz;
    }

    public static void main(String[] args) {
	    
    }  
}