package org.filipski;

import org.filipski.model.Model;
import org.filipski.view.GeneralRegistry;

public class Main {
    public static void main(String[] args) throws Throwable {
        if (args.length > 0)
            runWithOptions (args);
        else
            runDefault();
    }

    private static void runWithOptions(String[] args) throws Throwable
    {
        //noinspection SwitchStatementWithTooFewBranches
        switch (args[0]) {
            case "generate":
                Startup.dataBuilder();
                GeneralRegistry frame = new GeneralRegistry();
                frame.setVisible(true);
                return;
            case "resetdate":
                runResetDate();
                return;
            default:
                System.out.printf("Unrecognized command line option: %s%n", args[0]);
                System.out.println("   Recognized options: generate");
                //noinspection UnnecessaryReturnStatement
                return;
        }
    }

    private static void runDefault() throws Throwable {
        //Model.getModel().resetReferenceDate();
        Startup.dataLoader();
        GeneralRegistry frame = new GeneralRegistry();
        frame.setVisible(true);
    }
    private static void runResetDate() throws Throwable {
        Model.getModel().resetReferenceDate();
        Startup.dataLoader();
        GeneralRegistry frame = new GeneralRegistry();
        frame.setVisible(true);
    }
}