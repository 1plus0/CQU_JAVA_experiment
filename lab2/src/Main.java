import java.util.Scanner;

import manager.FileManager;

public class Main {
    // 主函数，程序的入口
    public static void main(String[] args) {
        // 用户可以通过命令行参数指定起始目录，默认为当前用户目录
        String initialDir = args.length > 0 ? args[0] : System.getProperty("user.dir");
        // 创建 FileManager 实例
        FileManager fm = new FileManager(initialDir);
        try (Scanner scanner = new Scanner(System.in)) {
            // 循环处理用户命令
            while (true) {
                System.out.print("[" + fm.getCurrentDirectory() + "]");//显示当前操作文件夹
                String command = scanner.next(); // 读取用户输入的命令
                switch (command) {
                    case "cd"://
                        String newPath = scanner.next(); // 读取新的目录路径
                        fm.setCurrentDirectory(newPath);
                        break;
                    case "ls": // 列出目录内容
                        fm.listDirectoryContents();
                        break;
                    case "mk": // 创建新文件
                        String fileName = scanner.next(); // 读取文件名
                        fm.createFile(fileName);
                        break;
                    case "mkdir"://新建文件夹
                        String DirectoryName = scanner.next();
                        fm.createDirectory(DirectoryName);
                        break;
                    case "rm": // 删除文件
                        String deleteFileName = scanner.next(); // 读取待删除的文件名
                        fm.deleteFile(deleteFileName);
                        break;
                    case "cat": // 显示文件内容
                        String displayFileName = scanner.next(); // 读取要显示内容的文件名
                        fm.displayFileContent(displayFileName);
                        break;
                    case "copy": // 拷贝文件
                        String sourceFile = scanner.next(); // 读取源文件名
                        System.out.println("Enter target file path:");
                        String targetFile = scanner.next(); // 读取目标文件路径
                        System.out.println("Execute in foreground (Y/N)?");//是否在前台打印进度
                        boolean isForegroundFile = scanner.next().equalsIgnoreCase("Y");
                        fm.copyFile(sourceFile, targetFile, isForegroundFile);
                        break;
                    case "copydir": // 拷贝文件夹
                        String sourceDir = scanner.next(); // 读取源文件夹名
                        System.out.println("Enter target directory path:");
                        String targetDir = scanner.next(); // 读取目标文件夹路径
                        System.out.println("Execute in foreground (Y/N)?");//是否在前台打印进度
                        boolean isForegroundDir = scanner.next().equalsIgnoreCase("Y");
                        fm.copyDirectory(sourceDir, targetDir, isForegroundDir);
                        break;
                    case "filter": // 过滤和排序文件
                        System.out.println("Enter filter (e.g. .txt, * for all):");
                        String filter = scanner.next(); // 读取过滤器
                        System.out.println("Enter sort by (name, size, date):");
                        String sortBy = scanner.next(); // 读取排序依据
                        System.out.println("Sort ascending? (true/false):");
                        boolean ascending = scanner.nextBoolean(); // 读取排序顺序
                        fm.listDirectoryContents(filter, sortBy, ascending);
                        break;
                    case "encrypt"://加密
                        String fileToEncrypt = scanner.next(); // 读取待加密的文件名
                        System.out.println("Enter encryption key (16 characters):");
                        String encryptKey = scanner.next(); // 读取加密密钥
                        System.out.println("Enter output file name:");
                        String outputEncryptFile = scanner.next(); // 读取输出文件名
                        fm.encryptFile(fileToEncrypt, encryptKey, outputEncryptFile);
                        break;
                    case "decrypt"://解密
                        String fileToDecrypt = scanner.next(); // 读取待解密的文件名
                        System.out.println("Enter decryption key (16 characters):");
                        String decryptKey = scanner.next(); // 读取解密密钥
                        System.out.println("Enter output file name:");
                        String outputDecryptFile = scanner.next(); // 读取输出文件名
                        fm.decryptFile(fileToDecrypt, decryptKey, outputDecryptFile);
                        break;
                    case "zip"://压缩
                        String sourceToZip = scanner.next(); // 读取待压缩的文件或目录名
                        System.out.println("Enter output zip file name:");
                        String zipFileName = scanner.next(); // 读取输出的 zip 文件名
                        fm.zip(sourceToZip, zipFileName);
                        break;
                    case "unzip"://解压缩
                        String zipFileToUnzip = scanner.next(); // 读取待解压的 zip 文件名
                        System.out.println("Enter output directory name:");
                        String outputDir = scanner.next(); // 读取输出目录名
                        fm.unzip(zipFileToUnzip, outputDir);
                        break;
                    case "exit": // 退出程序
                        System.out.println("Exiting program.");
                        fm.shutdown(); // 关闭线程池
                        System.exit(0);
                        break;
                    default: // 处理无效命令
                        System.out.println("Invalid command.");
                        break;
                }
            }
        }

        
    }
}