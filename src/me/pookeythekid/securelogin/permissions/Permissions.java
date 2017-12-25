package me.pookeythekid.securelogin.permissions;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

public class Permissions {
	
	public Permission allPerms = new Permission("securelogin.*");

	public Permission antifreeze = new Permission("securelogin.nofreeze");

	public Permission reload = new Permission("securelogin.reload");

	public Permission setLoginSpawn = new Permission("securelogin.setloginspawn");
	
	public Permission loginSpawn = new Permission("securelogin.loginspawn");
	
	public Permission loginSpawnOther = new Permission("securelogin.loginspawn.other");

	public Permission help = new Permission("securelogin.help");

	// public Permission setMessage = new Permission("securelogin.setmessage");

	// public Permission setMessageHelp = new Permission("securelogin.setmessage.help");

	public Permission seeSpawnWarning = new Permission("securelogin.seespawnwarning");
	
	public Permission login = new Permission("securelogin.login");
	
	public Permission register = new Permission("securelogin.register");
	
	public Permission createSQ = new Permission("securelogin.createsecurityquestion");
	
	public Permission createSA = new Permission("securelogin.createsecurityanswer");
	
	public Permission changeSQ = new Permission("securelogin.changesecurityquestion");
	
	public Permission changeSA = new Permission("securelogin.changesecurityanswer");
	
	public Permission changePassword = new Permission("securelogin.changepassword");
	
	public Permission getPassword = new Permission("securelogin.getpassword");
	
	public Permission getSQ = new Permission("securelogin.getsecurityquestion");
	
	public Permission getSA = new Permission("securelogin.getsecurityanswer");
	
	public Permission changePasswordOther = new Permission("securelogin.changepassword.other");
	
	public Permission changeSQOther = new Permission("securelogin.changesecurityquestion.other");
	
	public Permission changeSAOther = new Permission("securelogin.changesecurityanswer.other");
	
	
	public void registerPerms(PluginManager pm) {
		
		pm.addPermission(allPerms);
		pm.getPermission("securelogin.*").setDefault(PermissionDefault.OP);
		
		pm.addPermission(antifreeze);
		pm.getPermission("securelogin.nofreeze").addParent(allPerms, true);
		pm.getPermission("securelogin.nofreeze").setDefault(PermissionDefault.OP);
		
		pm.addPermission(reload);
		pm.getPermission("securelogin.reload").addParent(allPerms, true);
		pm.getPermission("securelogin.reload").setDefault(PermissionDefault.OP);
		
		pm.addPermission(setLoginSpawn);
		pm.getPermission("securelogin.setloginspawn").addParent(allPerms, true);
		pm.getPermission("securelogin.setloginspawn").setDefault(PermissionDefault.OP);
		
		pm.addPermission(loginSpawn);
		pm.getPermission("securelogin.loginspawn").addParent(allPerms, true);
		pm.getPermission("securelogin.loginspawn").setDefault(PermissionDefault.OP);
		
		pm.addPermission(loginSpawnOther);
		pm.getPermission("securelogin.loginspawn.other").addParent(allPerms, true);
		pm.getPermission("securelogin.loginspawn.other").setDefault(PermissionDefault.OP);
		
		pm.addPermission(help);
		pm.getPermission("securelogin.help").addParent(allPerms, true);
		pm.getPermission("securelogin.help").setDefault(PermissionDefault.TRUE);
		
		/*pm.addPermission(setMessage);
		pm.getPermission("securelogin.setmessage").addParent(allPerms, true);
		pm.getPermission("securelogin.setmessage").setDefault(PermissionDefault.OP);*/
		
		/*pm.addPermission(setMessageHelp);
		pm.getPermission("securelogin.setmessage.help").addParent(allPerms, true);
		pm.getPermission("securelogin.setmessage.help").setDefault(PermissionDefault.OP);*/
		
		pm.addPermission(seeSpawnWarning);
		pm.getPermission("securelogin.seespawnwarning").addParent(allPerms, true);
		pm.getPermission("securelogin.seespawnwarning").setDefault(PermissionDefault.OP);
		
		pm.addPermission(login);
		pm.getPermission("securelogin.login").addParent(allPerms, true);
		pm.getPermission("securelogin.login").setDefault(PermissionDefault.TRUE);
		
		pm.addPermission(register);
		pm.getPermission("securelogin.register").addParent(allPerms, true);
		pm.getPermission("securelogin.register").setDefault(PermissionDefault.TRUE);
		
		pm.addPermission(createSQ);
		pm.getPermission("securelogin.createsecurityquestion").addParent(allPerms, true);
		pm.getPermission("securelogin.createsecurityquestion").setDefault(PermissionDefault.TRUE);
		
		pm.addPermission(createSA);
		pm.getPermission("securelogin.createsecurityanswer").addParent(allPerms, true);
		pm.getPermission("securelogin.createsecurityanswer").setDefault(PermissionDefault.TRUE);
		
		pm.addPermission(changeSQ);
		pm.getPermission("securelogin.changesecurityquestion").addParent(allPerms, true);
		pm.getPermission("securelogin.changesecurityquestion").setDefault(PermissionDefault.TRUE);
		
		pm.addPermission(changeSA);
		pm.getPermission("securelogin.changesecurityanswer").addParent(allPerms, true);
		pm.getPermission("securelogin.changesecurityanswer").setDefault(PermissionDefault.TRUE);
		
		pm.addPermission(changePassword);
		pm.getPermission("securelogin.changepassword").addParent(allPerms, true);
		pm.getPermission("securelogin.changepassword").setDefault(PermissionDefault.TRUE);
		
		pm.addPermission(getPassword);
		pm.getPermission("securelogin.getpassword").addParent(allPerms, true);
		pm.getPermission("securelogin.getpassword").setDefault(PermissionDefault.OP);
		
		pm.addPermission(getSQ);
		pm.getPermission("securelogin.getsecurityquestion").addParent(allPerms, true);
		pm.getPermission("securelogin.getsecurityquestion").setDefault(PermissionDefault.OP);
		
		pm.addPermission(getSA);
		pm.getPermission("securelogin.getsecurityanswer").addParent(allPerms, true);
		pm.getPermission("securelogin.getsecurityanswer").setDefault(PermissionDefault.OP);
		
		pm.addPermission(changePasswordOther);
		pm.getPermission("securelogin.changepassword.other").addParent(allPerms, true);
		pm.getPermission("securelogin.changepassword.other").setDefault(PermissionDefault.OP);
		
		pm.addPermission(changeSQOther);
		pm.getPermission("securelogin.changesecurityquestion.other").addParent(allPerms, true);
		pm.getPermission("securelogin.changesecurityquestion.other").setDefault(PermissionDefault.OP);
		
		pm.addPermission(changeSAOther);
		pm.getPermission("securelogin.changesecurityanswer.other").addParent(allPerms, true);
		pm.getPermission("securelogin.changesecurityanswer.other").setDefault(PermissionDefault.OP);
		
		
	}
	
	public void removePerms(PluginManager pm) {
		
		pm.removePermission(allPerms);
		
		pm.removePermission(reload);
		
		pm.removePermission(setLoginSpawn);
		
		pm.removePermission(loginSpawn);
		
		pm.removePermission(help);
		
		// pm.removePermission(setMessage);
		
		// pm.removePermission(setMessageHelp);
		
		pm.removePermission(seeSpawnWarning);
		
		pm.removePermission(login);
		
		pm.removePermission(register);
		
		pm.removePermission(createSQ);
		
		pm.removePermission(createSA);
		
		pm.removePermission(changeSQ);
		
		pm.removePermission(changeSA);
		
		pm.removePermission(changePassword);
		
		pm.removePermission(getPassword);
		
		pm.removePermission(getSQ);
		
		pm.removePermission(getSA);
		
		pm.removePermission(changePasswordOther);
		
		pm.removePermission(changeSQOther);
		
		pm.removePermission(changeSAOther);
		
	}
	
	
}
