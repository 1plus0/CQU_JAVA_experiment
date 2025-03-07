package manager;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



public class FileManager {
    private Path currentDirectory; // 当前工作目录路径

    private static final String ALGORITHM = "AES"; // 加密算法
    private static final String TRANSFORMATION = "AES"; // 转换方式

    private ExecutorService executorService;//创造线程池

    
    

    // 构造函数，初始化当前目录
    public FileManager(String initialDir) {
        // 设置初始目录，如果路径无效，则使用默认目录
        currentDirectory = Paths.get(initialDir);
        // 检查路径是否存在且是否为目录
        if (!Files.exists(currentDirectory) || !Files.isDirectory(currentDirectory)) {
            // 如果路径无效，输出提示信息并使用用户当前工作目录
            System.out.println("Invalid directory path provided. Using default directory.");
            currentDirectory = Paths.get(System.getProperty("user.dir"));
        }
        
        //初始化线程池
        executorService = Executors.newCachedThreadPool();
    }

    // 设置当前工作目录
    public void setCurrentDirectory(String path) {
        Path newDirectory = Paths.get(path);
        // 检查路径是否存在且是否为目录
        if (Files.exists(newDirectory) && Files.isDirectory(newDirectory)) {
            currentDirectory = newDirectory;
        } else {
            System.out.println("Invalid directory path.");
        }
    }

    // 获取当前工作目录
    public Path getCurrentDirectory() {
        return currentDirectory;
    }


    // 列出当前目录中的所有文件和子目录
    public void listDirectoryContents() {
        listDirectoryContents("*", "name", true);
    }

    // 根据条件列出当前目录中的文件和子目录
    public void listDirectoryContents(String filter, String sortBy, boolean ascending) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory)) {
            List<Path> paths = new ArrayList<>();
            for (Path filePath : stream) {
                if (filter.equals("*") || filePath.getFileName().toString().contains(filter)) {
                    paths.add(filePath);
                }
            }

// 选择排序方式
            Comparator<Path> comparator;

            // 使用 if-else 语句来选择排序方式
            if ("size".equalsIgnoreCase(sortBy)) {
                comparator = new Comparator<Path>() {
                    @Override
                    public int compare(Path p1, Path p2) {
                        long size1 = getFileSize(p1);
                        long size2 = getFileSize(p2);
                        return Long.compare(size1, size2);
                    }
                };
            } else if ("date".equalsIgnoreCase(sortBy)) {
                comparator = new Comparator<Path>() {
                    @Override
                    public int compare(Path p1, Path p2) {
                        String dateStr1 = getFileDate(p1); // 获取第一个文件格式化的日期字符串
                        String dateStr2 = getFileDate(p2); // 获取第二个文件格式化的日期字符串
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定义日期格式
                            Date date1 = sdf.parse(dateStr1); // 将字符串转换为 Date 对象
                            Date date2 = sdf.parse(dateStr2); // 将字符串转换为 Date 对象
                            return date1.compareTo(date2); // 进行日期比较
                        } catch (ParseException e) {
                            return 0; // 如果解析失败，返回0表示两个日期相等（或根据需要处理）
                        }
                    }
                };
            } else {
                comparator = new Comparator<Path>() {
                    @Override
                    public int compare(Path p1, Path p2) {
                        return p1.getFileName().toString().compareTo(p2.getFileName().toString());
                    }
                };
            }
            //倒序
            if (!ascending) {
                comparator = comparator.reversed();
            }

            paths.sort(comparator);

            // 输出文件信息
            System.out.printf("%-40s %-15s %-20s %-20s%n", "File Name", "Size (bytes)", "Type", "Last Modified");
            System.out.println("--------------------------------------------------------------------------------");
            for (Path path : paths) {
                System.out.printf("%-40s %-15d %-20s %-20s%n", 
                                  path.getFileName(), 
                                  getFileSize(path), 
                                  getFileType(path), 
                                  getFileDate(path));
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.out.println("Error reading directory contents");
        }
    }

    // 获取文件大小
    private static long getFileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return -1; // 返回 -1 表示获取大小失败
        }
    }

    // 获取文件类型
    private static String getFileType(Path path) {
        String fileType = "*";
        try {
            fileType = Files.probeContentType(path);
        } catch (IOException e) {
            System.out.println("Failed to determine file type for: " + path);
        }
        return fileType != null ? fileType : "unknown";
    }

    // 获取文件最后修改时间
    private static String getFileDate(Path path) {
    try {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        long millis = attrs.lastModifiedTime().toMillis();
        Date date = new Date(millis); // 将毫秒转为 Date 对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定义日期格式
        return sdf.format(date); // 格式化日期并返回
    } catch (IOException e) {
        return "Unable to retrieve date"; // 返回错误信息
    }
   }



    // 创建新文件
    public void createFile(String fileName) {
        // 构造新文件的完整路径
        Path newFilePath = currentDirectory.resolve(fileName);
        try {
            // 创建新文件
            Files.createFile(newFilePath);
            // 输出文件创建成功的信息
            System.out.println("File created: " + newFilePath);
        } catch (IOException e) {
            // 捕获异常并输出错误信息
            System.out.println("Failed to create file: " + newFilePath);
        }
    }

    //创建新文件夹
    public void createDirectory(String folderName) {
        Path path = Paths.get(folderName);
        try {
            Files.createDirectories(path);
            System.out.println("目录创建成功: " + path.toString());
        } catch (IOException e) {
            System.err.println("创建目录失败: " + e.getMessage());
        }
    }
    // 删除指定文件
    public void deleteFile(String fileName) {
        // 构造待删除文件的完整路径
        Path fileToDelete = currentDirectory.resolve(fileName);
        try {
            // 删除文件
            Files.delete(fileToDelete);
            // 输出文件删除成功的信息
            System.out.println("File deleted: " + fileToDelete);
        } catch (IOException e) {
            // 捕获异常并输出错误信息
            System.out.println("Failed to delete file: " + fileToDelete);
        }
    }

     // 显示文本文件的内容
     public void displayFileContent(String fileName) {
        Path filePath = currentDirectory.resolve(fileName);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            System.out.println("Contents of the file: " + filePath);
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + filePath);
        }
    }


     // 拷贝文件
     public void copyFile(String sourceFileName, String targetFilePath, boolean isForeground) {
        // 获取源文件的路径
        Path sourceFile = currentDirectory.resolve(sourceFileName);
        // 获取目标文件的路径
        Path targetFile = Paths.get(targetFilePath);
    
        // 创建一个拷贝任务
        Runnable copyTask = () -> {
            try {
                // 获取源文件的总字节数
                long totalBytes = Files.size(sourceFile);
                long copiedBytes = 0; // 已复制的字节数
    
                // 使用 BufferedInputStream 从源文件读取数据，并使用 FileOutputStream 写入到目标文件
                try (InputStream in = new BufferedInputStream(Files.newInputStream(sourceFile));
                     FileOutputStream out = new FileOutputStream(targetFile.toFile())) {
                    
                    byte[] buffer = new byte[1024]; // 创建缓冲区
                    int bytesRead; // 每次读取的字节数
                    
                    // 循环读取源文件，直到文件结束
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead); // 写入目标文件
                        copiedBytes += bytesRead; // 更新已复制的字节数
                        if (isForeground) {
                             // 更新进度
                        printProgress(copiedBytes, totalBytes);
                        }
    
                       
                    }
                }
    
                if (isForeground) {
                    // 输出拷贝完成的信息
                    System.out.println("\nFile copied from " + sourceFile + " to " + targetFile);
                }
                
                
            } catch (IOException e) {
                // 捕获并处理 IO 异常
                System.out.println("Failed to copy file: " + e.getMessage());
            }
        };
    
        // 判断是否在前台执行拷贝任务
        if (isForeground) {
            // 在前台执行拷贝任务
            copyTask.run();
        } else {
            // 在后台执行拷贝任务
            executorService.execute(copyTask);
            System.out.println("Copy task started in the background.");
        }
    }

    // 拷贝文件夹
    public void copyDirectory(String sourceDirName, String targetDirPath, boolean isForeground) {
        // 获取源目录的路径
        Path sourceDir = currentDirectory.resolve(sourceDirName);
        // 获取目标目录的路径
        Path targetDir = Paths.get(targetDirPath);
    
        // 创建一个拷贝目录的任务
        Runnable copyDirTask = () -> {
            try {
                // 统计源目录中的文件总数
                long totalFiles = Files.walk(sourceDir).count();
                long copiedFiles = 0; // 已复制的文件数
    
                // 创建目标目录
                Files.createDirectories(targetDir);
                try {
                    // 遍历源目录中的文件和子目录
                    for (Path source : Files.walk(sourceDir).toArray(Path[]::new)) {
                        // 计算目标路径
                        Path target = targetDir.resolve(sourceDir.relativize(source));
    
                        // 如果是目录，创建目录；如果是文件，复制文件
                        if (Files.isDirectory(source)) {
                            Files.createDirectories(target); // 创建目标目录
                        } else {
                            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING); // 复制文件
                        }
                        
                        copiedFiles++; // 更新已复制的文件数
                        
                        // 更新进度
                        // 仅在前台模式下打印进度
                        if (isForeground) {
                            printProgress(copiedFiles, totalFiles);
                        }
                    }
                } catch (IOException e) {
                    // 捕获并处理 IO 异常
                    System.out.println("Failed to copy directory: " + e.getMessage());
                }
                if (isForeground) {
                    printProgress(copiedFiles, totalFiles);
                    // 输出拷贝完成的信息
                    System.out.println("\nDirectory copied from " + sourceDir + " to " + targetDir);
                }
                
            } catch (IOException e) {
                // 捕获并处理 IO 异常
                System.out.println("Failed to copy directory: " + e.getMessage());
            }
        };
    
        // 判断是否在前台执行拷贝任务
        if (isForeground) {
            // 在前台执行拷贝任务
            copyDirTask.run();
        } else {
            // 在后台执行拷贝任务
            executorService.execute(copyDirTask);
            System.out.println("Copy task started in the background.");
        }
    }

    // 打印进度
    private void printProgress(long completed, long total) {
        int progressWidth = 50; // 进度条的宽度
        int completedBars = (int) ((completed * progressWidth) / total);
        StringBuilder progress = new StringBuilder("[");
        
        for (int i = 0; i < progressWidth; i++) {
            if (i < completedBars) {
                progress.append("=");
            } else {
                progress.append(" ");
            }
        }
        
        progress.append("] ");
        progress.append(String.format("%.2f%%", (double) completed / total * 100));
        
        // 使用 \r 让光标回到行首并覆盖显示
        System.out.print("\r" + progress.toString());
    }

    // 关闭线程池
    public void shutdown() {
        executorService.shutdown();
    }


    // 加密文件
    public void encryptFile(String fileName, String key, String outputFileName) {
        // 获取要加密的文件路径
        Path fileToEncrypt = currentDirectory.resolve(fileName);
        // 获取加密后输出文件的路径
        Path encryptedFile = currentDirectory.resolve(outputFileName);
        try {
            // 创建 Cipher 对象，指定加密算法和模式
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 创建 SecretKeySpec 对象，用于指定加密密钥
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            // 初始化 Cipher 为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    
            // 使用文件输入流和输出流进行文件加密
            try (InputStream inputStream = Files.newInputStream(fileToEncrypt);
                 OutputStream outputStream = Files.newOutputStream(encryptedFile)) {
                byte[] inputBytes = new byte[64]; // 缓冲区，用于读取文件
                int bytesRead;
    
                // 循环读取文件并加密
                while ((bytesRead = inputStream.read(inputBytes)) != -1) {
                    // 进行加密操作
                    byte[] outputBytes = cipher.update(inputBytes, 0, bytesRead);
                    // 如果有输出字节，写入到输出文件
                    if (outputBytes != null) {
                        outputStream.write(outputBytes);
                    }
                }
                // 处理最后的加密块
                byte[] outputBytes = cipher.doFinal();
                if (outputBytes != null) {
                    outputStream.write(outputBytes); // 写入最后的输出字节
                }
                System.out.println("File encrypted: " + encryptedFile);
            }
        } catch (Exception e) {
            // 捕获并处理异常
            System.out.println("Failed to encrypt file: " + e.getMessage());
        }
    }

    // 解密文件
    public void decryptFile(String fileName, String key, String outputFileName) {
        // 获取要解密的文件路径
        Path fileToDecrypt = currentDirectory.resolve(fileName);
        // 获取解密后输出文件的路径
        Path decryptedFile = currentDirectory.resolve(outputFileName);
        try {
            // 创建 Cipher 对象，指定解密算法和模式
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 创建 SecretKeySpec 对象，用于指定解密密钥
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            // 初始化 Cipher 为解密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
    
            // 使用文件输入流和输出流进行文件解密
            try (InputStream inputStream = Files.newInputStream(fileToDecrypt);
                 OutputStream outputStream = Files.newOutputStream(decryptedFile)) {
                byte[] inputBytes = new byte[64]; // 缓冲区，用于读取文件
                int bytesRead;
    
                // 循环读取文件并解密
                while ((bytesRead = inputStream.read(inputBytes)) != -1) {
                    // 进行解密操作
                    byte[] outputBytes = cipher.update(inputBytes, 0, bytesRead);
                    // 如果有输出字节，写入到输出文件
                    if (outputBytes != null) {
                        outputStream.write(outputBytes);
                    }
                }
                // 处理最后的解密块
                byte[] outputBytes = cipher.doFinal();
                if (outputBytes != null) {
                    outputStream.write(outputBytes); // 写入最后的输出字节
                }
                System.out.println("File decrypted: " + decryptedFile);
            }
        } catch (Exception e) {
            // 捕获并处理异常
            System.out.println("Failed to decrypt file: " + e.getMessage());
        }
    }


    // 压缩文件或文件夹
    public void zip(String source, String outputFileName) {
        // 获取要压缩的源路径
        Path sourcePath = currentDirectory.resolve(source);
        // 获取输出的压缩文件路径
        Path zipFile = currentDirectory.resolve(outputFileName);
        
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            // 规范化源路径
            Path sourcePathNormalized = sourcePath.normalize();
            // 调用递归方法压缩文件
            zipFile(sourcePathNormalized, sourcePathNormalized.getFileName().toString(), zos);
            System.out.println("Compressed: " + zipFile);
        } catch (IOException e) {
            // 捕获并处理 IO 异常
            System.out.println("Failed to zip: " + e.getMessage());
        }
    }

    private void zipFile(Path fileToZip, String fileName, ZipOutputStream zos) throws IOException {
        // 检查给定路径是否为目录
        if (Files.isDirectory(fileToZip)) {
            // 使用 DirectoryStream 遍历目录中的每个文件
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(fileToZip)) {
                // 遍历目录中的每个子文件/子目录
                for (Path child : stream) {
                    // 递归调用 zipFile 方法以压缩子文件/子目录
                    zipFile(child, fileName + "/" + child.getFileName(), zos);
                }
            }
        } else {
            // 如果是文件，打开输入流进行读取
            try (InputStream is = Files.newInputStream(fileToZip)) {
                // 创建 ZipEntry 对象，表示当前文件在压缩包中的条目
                ZipEntry zipEntry = new ZipEntry(fileName);
                // 将条目加入到 ZipOutputStream 中
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024]; // 缓冲区
                int length;
                // 读取文件内容并写入压缩流
                while ((length = is.read(buffer)) >= 0) {
                    zos.write(buffer, 0, length);
                }
                // 完成当前条目
                zos.closeEntry();
            }
        }
    }

    // 解压缩文件
    public void unzip(String zipFileName, String outputDir) {
        // 获取 ZIP 文件的路径
        Path zipFilePath = currentDirectory.resolve(zipFileName);
        // 获取输出目录的路径
        Path outputDirectory = currentDirectory.resolve(outputDir);
    
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFilePath))) {
            ZipEntry zipEntry;
            // 循环读取 ZIP 文件中的每个条目
            while ((zipEntry = zis.getNextEntry()) != null) {
                // 创建解压后的文件路径
                Path newPath = outputDirectory.resolve(zipEntry.getName());
                // 如果条目是目录，则创建目录
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(newPath);
                } else {
                    // 确保父目录存在
                    Files.createDirectories(newPath.getParent());
                    // 创建输出流以写入解压后的文件
                    try (OutputStream os = Files.newOutputStream(newPath)) {
                        byte[] buffer = new byte[1024]; // 缓冲区
                        int length;
                        // 读取 ZIP 输入流并写入到输出文件
                        while ((length = zis.read(buffer)) >= 0) {
                            os.write(buffer, 0, length);
                        }
                    }
                }
                // 关闭当前条目
                zis.closeEntry();
            }
            System.out.println("Unzipped: " + zipFilePath + " to " + outputDirectory);
        } catch (IOException e) {
            // 捕获并处理 IO 异常
            System.out.println("Failed to unzip: " + e.getMessage());
        }
    }


    
}