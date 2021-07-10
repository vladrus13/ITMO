package tests;

import ru.vladrus13.system.launcher.ProgramLauncher;

import java.nio.file.Path;

public class NullProgramLauncher implements ProgramLauncher {
    @Override
    public ProcessBuilder launcher(Path path, Path path1) {
        return null;
    }
}
