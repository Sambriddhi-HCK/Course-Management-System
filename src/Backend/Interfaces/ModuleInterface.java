package Backend.Interfaces;

/*
 * CRUD operations for modules
 */

import Backend.Details.ModuleDetails;
import java.util.HashMap;
public interface ModuleInterface {
    public boolean createModule(ModuleDetails module);
    public boolean updateModule(ModuleDetails module, String newCode);
    public boolean removeModule(String moduleCode);
    public HashMap<String, String> moduleList();
    public ModuleDetails getModulesDetails(String moduleCode);

}
