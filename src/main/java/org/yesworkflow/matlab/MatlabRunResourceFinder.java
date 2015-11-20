package org.yesworkflow.matlab;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.yesworkflow.data.UriTemplate;
import org.yesworkflow.recon.ResourceFinder;

public class MatlabRunResourceFinder extends SimpleFileVisitor<Path> implements ResourceFinder {
    
    private FileSystem fileSystem = FileSystems.getDefault();
    private PathMatcher matcher;
    private Path basePath;
    private List<String> patternMatches;
    
    public Collection<String> findResources(String baseUri, UriTemplate uriTemplate) {
        this.basePath = Paths.get(baseUri);
        patternMatches = new LinkedList<String>();
        Path resourceSearchBase = this.basePath.resolve(uriTemplate.leadingPath);
        if (Files.isRegularFile(resourceSearchBase)) {
            patternMatches.add(this.basePath.relativize(resourceSearchBase).toString());
        } else {
            this.matcher = fileSystem.getPathMatcher(uriTemplate.getGlobPattern());
            try {
                Files.walkFileTree(resourceSearchBase, this);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }              
        }
        return patternMatches;
    }
        
    @Override public FileVisitResult visitFile(Path filePath, BasicFileAttributes fileAttributes) throws IOException {
      Path runRelativePath = basePath.relativize(filePath);
      if (matcher.matches(runRelativePath)) {
          patternMatches.add(runRelativePath.toString());
      }
      return FileVisitResult.CONTINUE;
    }
    
    @Override  public FileVisitResult preVisitDirectory(Path directoryPath, BasicFileAttributes fileAttributes) throws IOException {
      // TODO: Improve performance by detecting when directory cannot lead to a match for the template
        return FileVisitResult.CONTINUE;
    }
    
}