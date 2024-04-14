package org.filipski;

import org.filipski.schema.SmartphoneRegistry;
import org.filipski.view.GeneralRegistry;
import org.filipski.model.Model;

import java.util.List;

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
                return;
            default:
                System.out.printf("Unrecognized command line option: %s%n", args[0]);
                System.out.println("   Recognized options: generate");
                //noinspection UnnecessaryReturnStatement
                return;
        }
    }

    private static void runDefault() throws Throwable {
        Startup.dataLoader();
        List<SmartphoneRegistry> registry = Model.getModel().getRegistry();
        for (var r : registry) System.out.println(r.getName());
        GeneralRegistry frame = new GeneralRegistry();
        frame.setVisible(true);
    }
}