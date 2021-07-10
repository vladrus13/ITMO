import ru.vladrus13.system.interactor.Interactor;
import ru.vladrus13.system.launcher.ProgramLauncher;
import ru.vladrus13.system.log.LogSettings;
import ru.vladrus13.system.result.LimitsPack;
import ru.vladrus13.system.result.Result;
import ru.vladrus13.system.result.ResultType;
import ru.vladrus13.system.result.Table;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NullInteractorLauncher {
    public static Table generate(final ProgramLauncher programLauncher, final Collection<Interactor> interactors,
                                 final ExecutorService executorService, final LimitsPack limitsPack) throws InterruptedException {
        Table table = new Table();
        int launch = 0;
        AtomicInteger complete = new AtomicInteger(0);
        for (Interactor interactor : interactors) {
            // if (LogSettings.isLog) System.out.println("=== Launch === " + interactor.toString());
            launch++;
            executorService.submit(() -> {
                try {
                    Result result = generate(programLauncher, interactor, limitsPack);
                    if (result.resultType != ResultType.OK) {
                        if (LogSettings.isLog) System.out.println("ban on " + interactor + ". " + result.resultType + " " + result.result);
                    }
                    table.addResult(interactor.toString(), 0, result);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    complete.incrementAndGet();
                }
            });
        }
        // TODO make with Phaser
        while (launch != complete.get()) {
            if (LogSettings.isLog) System.out.printf("Launched: %d, Complete: %d\n", launch, complete.get());
            Thread.sleep(1000);
        }
        if (LogSettings.isLog) System.out.println("Done. Shutdown executor...");
        executorService.shutdown();
        boolean isTerminate = executorService.awaitTermination(1L, TimeUnit.SECONDS);
        if (!isTerminate) {
            if (LogSettings.isLog) System.out.println("Process not terminated. Shutdown force");
            executorService.shutdownNow();
        }
        return table;
    }

    private static Result generate(final ProgramLauncher programLauncher, Interactor interactor,
                                   final LimitsPack limitsPack) {
        ProcessBuilder processBuilder = programLauncher.launcher(null, null);
        Process process;
        return interactor.test(null, limitsPack);
    }

}
