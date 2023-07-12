const fs = require('fs');


const repoFolder = "C:\\MYDEV\\Repos\\git\\EquipmentTest.Android\\Workspace\\Technogym.EquipmentTest.Android\\"

const commandFolder = `${repoFolder}systemModule\\src\\main\\java\\com\\technogym\\android\\myrun\\sdk\\system\\commands\\bleUart\\`
const commandPackage = "com.technogym.android.myrun.sdk.system.commands.bleUart;";

const notifierFolder = `${repoFolder}systemModule\\src\\main\\java\\com\\technogym\\android\\myrun\\sdk\\system\\listeners\\notifiers\\bleUart\\`
const notifierPackage = "com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart";

const notificationRuleFolder = `${repoFolder}systemModule\\src\\main\\java\\com\\technogym\\android\\myrun\\sdk\\system\\notificationRules\\bleUart\\`
const notificationRulePackage = "com.technogym.android.myrun.sdk.system.notificationRules.bleUart";

const systemListenerInterfaceFile = `${repoFolder}systemModule\\src\\main\\java\\com\\technogym\\android\\myrun\\sdk\\system\\listeners\\ISystemListener.java`
const systemProxyInterfaceFile = `${repoFolder}systemModule\\src\\main\\java\\com\\technogym\\android\\myrun\\sdk\\system\\proxies\\ISystemProxy.java`


const commands = [
    ["KeyButton", "KEYBUTTON"],
];



let addNotificationRulesCode = ``;
let commandsImplementationCode = "";

// Execution
commands.forEach(entry => {
    generateSingleCommand(entry);
});
console.log();
console.log();
console.log(addNotificationRulesCode);
console.log();
console.log();
console.log(commandsImplementationCode);


function generateSingleCommand([commandName, commandString]) {
    console.log(`\n# Generating ${commandName} (${commandString})`);
    const notifierClassName = `${commandName}Notifier`;
    const notificationRuleClassName = `${commandName}NotificationRule`;
    const commandClassName = `${commandName}Command`;
    generateCommandClass(commandName, commandString, commandClassName);
    generateNotifierClass(notifierClassName, commandName);
    generateNotificationRuleClass(commandString, notifierClassName, notificationRuleClassName);
    updateSystemInterface(commandName);
    updateProxyInterface(commandName);
    appendCodeSnippets(notificationRuleClassName, commandClassName, commandName);
}


/** Comando */
function generateCommandClass(commandName, commandString, className) {
    const filePath = `${commandFolder}${className}.java`;
    const code = `package ${commandPackage};
    
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class ${className} extends SystemCommand {

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected ${className}() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static ${className} create() {
        return new ${className}();
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "${commandString}";
    }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}`;
    fs.writeFileSync(filePath, code);
    console.log(`+ ${filePath}`);
}

function generateNotifierClass(className, commandName) {
    const filePath = `${notifierFolder}${className}.java`;
    const code = `package ${notifierPackage};

    import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
    import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
    
    public class ${className} extends Notifier<ISystemListener> {
        private final String mStatus;
    
        // { Construction
    
        /**
         * The class constructor.<br/>
         * It does nothing more than calling {@code super} constructor and initializing the class properties.
         *
         * @param status
         *            : the down sensor status retreived from the equipment
         * */
        protected ${className}(final String status) {
            super();
    
            this.mStatus = status;
        }
    
        /**
         * It's a static factory method which returns a new instance of the class.
         *
         * @param status
         *            : the parameter required by class constructor
         * */
        public static ${className} create(final String status) {
            return new ${className}(status);
        }
    
        // }
    
        // { INotifier implementation
    
        @Override
        public void notify(final ISystemListener listener) {
            listener.on${commandName}Response(this.mStatus);
        }
    
        // }
    }    
    `;
    fs.writeFileSync(filePath, code);
    console.log(`+ ${filePath}`);
}

function generateNotificationRuleClass(commandString, notifierClassName, className) {
    const filePath = `${notificationRuleFolder}${className}.java`;
    const code = `package ${notificationRulePackage};

    import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
    import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
    import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
    import ${notifierPackage.slice(0, notifierPackage.length)}.${notifierClassName};
    
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;
    
    public class ${className} extends NotificationRule<ISystemListener> {
    
        // { Construction
    
        protected ${className}() {
            super();
        }
    
        public static ${className} create() {
            return new ${className}();
        }
    
        // }
    
        // { NotificationRule abstract methods implementation
    
        @Override
        public String getIdentifier() {
            return "${commandString}";
        }
    
        @Override
        public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
            //final Iterator<String[]> result = this.splitParams(notifyMessage);
            //final String[] statusData = result.next();
    
            final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
            notifiers.add(${notifierClassName}.create(notifyMessage));
    
            return notifiers.iterator();
        }
    
        @Override
        protected String getRegex() {
            return "";
        }
    
        // }
        
    }      
    `;
    fs.writeFileSync(filePath, code);
    console.log(`+ ${filePath}`);
}


function appendCodeSnippets(notificationRuleClassName, commandClassName, commandName) {
    addNotificationRulesCode += `this.addNotificationRule(${notificationRuleClassName}.create());\n`;
    commandsImplementationCode += `@Override
public void send${commandName}()throws WriteNotAllowedException {
    this.sendCommand(${commandClassName}.create());
}\n\n`
}


function updateSystemInterface(commandName) {
    const codeToAdd = `public void on${commandName}Response(String response);`;

    const currentContent = fs.readFileSync(systemListenerInterfaceFile, "utf-8");
    const contentCleaned = currentContent
        .replaceAll("\n}", "")
        .replaceAll(codeToAdd, "");
    const fullFileCode = `${contentCleaned}${codeToAdd}\n}`;
    fs.writeFileSync(systemListenerInterfaceFile, fullFileCode);
    console.log(`o ${systemListenerInterfaceFile}`);
}


function updateProxyInterface(commandName) {
    const codeToAdd = `void send${commandName}() throws WriteNotAllowedException;`;

    const currentContent = fs.readFileSync(systemProxyInterfaceFile, "utf-8");
    const contentCleaned = currentContent
        .replaceAll("\n}", "")
        .replaceAll(codeToAdd, "");
    const fullFileCode = `${contentCleaned}${codeToAdd}\n}`;
    fs.writeFileSync(systemProxyInterfaceFile, fullFileCode);
    console.log(`o ${systemProxyInterfaceFile}`);
}