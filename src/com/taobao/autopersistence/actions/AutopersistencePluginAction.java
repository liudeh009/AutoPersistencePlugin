package com.taobao.autopersistence.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import com.taobao.autopersistence.wizards.AutoPersistenceWizard;

/**
 * 
 * 类AutopersistencePluginAction.java的实现描述：核心类
 * @author zhanzui.ldh 2013-5-20 上午10:09:43
 */
public class AutopersistencePluginAction implements IObjectActionDelegate {
	private Shell shell;
    private ISelection selection;
	
	/**
	 * Constructor for Action1.
	 */
	public AutopersistencePluginAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
	    String pjPath = getLocation();
	    AutoPersistenceWizard autoPersistenceWizard = new AutoPersistenceWizard(pjPath);
	    WizardDialog dialog = new WizardDialog(shell, autoPersistenceWizard);
        dialog.open();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
	}
    
    private final String getLocation() {
        if (selection == null) return null;
        if (selection instanceof IStructuredSelection) {
            Object sel = ((IStructuredSelection) selection).getFirstElement();
            if (sel instanceof PlatformObject) {
                PlatformObject p = (PlatformObject) sel;
                IResource resource = (IResource) p.getAdapter(IResource.class);
                if (resource != null) return getPath(resource);
            }
        }
        return null;
    }

    private final String getPath(IResource resource) {
        IPath loc = resource.getLocation();
        if (loc != null) return loc.toOSString();
        else return null;
    }
}
