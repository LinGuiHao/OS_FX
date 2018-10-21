package test.testdirector;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private TreeView dirTreeView;//创建一个树的视图

    private TreeItem item;//目录树item
    private ContextMenu menu = new ContextMenu();//右键栏目

    public void init(){
        //设置一个文件夹图标
        ImageView folderIcon = new ImageView();
        Image folderImage = new Image(getClass().getResourceAsStream("folder.jpg"));
        folderIcon.setImage(folderImage);
        folderIcon.setFitWidth(16);
        folderIcon.setFitHeight(16);
        //实例化目录树item
        item = new TreeItem<>("根目录");
        item.setGraphic(folderIcon);
        //设置缩放
        item.setExpanded(true);
        //将item加入到是视图中
        dirTreeView.setRoot(item);
        for(int i = 0;i < 5;i++){
            //添加树的节点，此处为跟节点添加子节点，同样可以通过此方法给子节点添加解点
            TreeItem item1 = new TreeItem<>("节点:" + i);
            item.getChildren().add(item1);
        }
        //设置右键添加节点
        MenuItem addItem = new MenuItem("添加节点");
        addItem.setOnAction(e ->{
            item.getChildren().add(new TreeItem<>("节点:" + item.getChildren().size()));
        });
        menu.getItems().add(addItem);

        dirTreeView.setContextMenu(menu);

    }

}
