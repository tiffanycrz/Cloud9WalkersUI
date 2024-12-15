package application;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.*;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
      Scene scene = new Scene(sceneOne(primaryStage));
      scene.getStylesheets().add("mystyle.css");
      primaryStage.setScene(scene);
      primaryStage.setFullScreen(true);
      primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
      primaryStage.setResizable(true);
      primaryStage.show();
    }

    public VBox sceneOne(Stage primaryStage) {
      // Create the menu bar
      MenuBar menuBar = new MenuBar();

      // Create menus
      Label homeMenuLabel = new Label("Home");
      Menu homeMenu = new Menu(null, homeMenuLabel);
      Menu forSaleMenu = new Menu("For Sale");
      Label storiesMenuLabel = new Label("Stories");
      Menu storiesMenu = new Menu(null, storiesMenuLabel);
      Label aboutMenuLabel = new Label("About");
      Menu aboutMenu = new Menu(null, aboutMenuLabel);

      // Create menu items
      MenuItem browseForSaleMenuItem1 = new MenuItem("Horses");
      MenuItem browseForSaleMenuItem2 = new MenuItem("Foals");

      // Add menu items to the respective menus
      forSaleMenu.getItems().addAll(browseForSaleMenuItem1,browseForSaleMenuItem2);

      // Add menus to the menu bar
      menuBar.getMenus().addAll(homeMenu, forSaleMenu, storiesMenu, aboutMenu);
      menuBar.setPadding(new Insets(10));

      // Add logo and company name
      Image logoImage = new Image("file:./res/horselogo.png");
      ImageView logoImageView = new ImageView(logoImage);
      logoImageView.setFitHeight(65);
      logoImageView.setPreserveRatio(true);
      Label websiteName = new Label("Cloud 9 Walkers");
      websiteName.setPadding(new Insets(15));
      websiteName.getStyleClass().add("menu-bar .label");
      websiteName.setStyle("-fx-font-size: 30");

      // Arrange menu bar area
      Region menuSpacer = new Region();
      menuSpacer.getStyleClass().add("menu-bar");
      HBox.setHgrow(menuSpacer, Priority.SOMETIMES);
      HBox menuBarContainer = new HBox(logoImageView, websiteName, menuSpacer, menuBar);
      menuBarContainer.setAlignment(Pos.CENTER);

      // Set menu bar area to be at top of the page
      BorderPane root = new BorderPane();
      root.setTop(menuBarContainer);

      // Introduction section
      Image bgImage = new Image("file:./res/background.png");
      ImageView bgImageView = new ImageView(bgImage);
      bgImageView.fitWidthProperty().bind(primaryStage.widthProperty());

      bgImageView.setPreserveRatio(true);
      Label purposeStatementLabel = new Label("Producing quality horses\n"
          + "with extensive traveling, camping, and trail experience\n"
          + "since 1983") ;
      purposeStatementLabel.getStyleClass().add("purpose-statement-label");
      purposeStatementLabel.setTextAlignment(TextAlignment.CENTER);
      StackPane introContainer = new StackPane(bgImageView, purposeStatementLabel);

      Button horseButton = new Button("Horses");
      Button foalButton = new Button("Foals");
      horseButton.getStyleClass().add("category-buttons");
      foalButton.getStyleClass().add("category-buttons");
      horseButton.setOnMouseEntered(event -> {
        horseButton.setStyle("-fx-text-fill: lightblue; -fx-border-color: lightblue;");
      }); 
      horseButton.setOnMouseExited(event -> {
        horseButton.setStyle("-fx-text-fill: black; -fx-border-color: black;");
      });
      foalButton.setOnMouseEntered(event -> {
        foalButton.setStyle("-fx-text-fill: lightblue; -fx-border-color: lightblue;");
      });
      foalButton.setOnMouseExited(event -> {
        foalButton.setStyle("-fx-text-fill: black; -fx-border-color: black;");
      });
      HBox buttonContainer = new HBox(50, horseButton, foalButton);
      buttonContainer.setAlignment(Pos.CENTER);
      buttonContainer.setPadding(new Insets(10));

      // Pagination to preview horses for sale
      ArrayList<GridPane> horsesForSaleArr = new ArrayList<>();
      addHorsesInfo(horsesForSaleArr);
      Pagination horsesPagination = new Pagination(3);
      horsesPagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
      horsesPagination.setPadding(new Insets(0));
      horsesPagination.setPageFactory((pageIndex) -> {
        return horsesForSaleArr.get(pageIndex);
      });

      // Pagination to preview foals for sale
      ArrayList<GridPane> babiesForSaleArr = new ArrayList<>();
      addBabiesInfo(babiesForSaleArr);
      Pagination babiesPagination = new Pagination(2);
      babiesPagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
      babiesPagination.setPadding(new Insets(0));
      babiesPagination.setPageFactory((pageIndex) -> {
        return babiesForSaleArr.get(pageIndex);
      });

      VBox forSalePreviewContainer = new VBox(buttonContainer, horsesPagination);
      forSalePreviewContainer.setAlignment(Pos.CENTER);
      forSalePreviewContainer.setPadding(new Insets(25));
      VBox.setVgrow(buttonContainer, Priority.ALWAYS);

      // Have pagination displayed change depending on button clicked
      horseButton.setOnAction(event -> {
        forSalePreviewContainer.getChildren().remove(1);
        forSalePreviewContainer.getChildren().add(horsesPagination);
      });
      foalButton.setOnAction(event -> {
        forSalePreviewContainer.getChildren().remove(1);
        forSalePreviewContainer.getChildren().add(babiesPagination);
      });

      // Display statement
      Label statementLabel1 = new Label("\nWe subscribe to the philosophy and practice of wet saddle blankets.\n");
      statementLabel1.setTextAlignment(TextAlignment.CENTER);
      Separator separator = new Separator();
      separator.setMaxWidth(200);
      menuSpacer.getStyleClass().add("statement-label1");
      Label statementLabel2 = new Label("We train our horses to:\n"
          + "Flex and bend at the poll\n"
          + "Back on light rein\n"
          + "Neck rein\n"
          + "Forehand turn\n"
          + "Pivot\n"
          + "Side pass\n"
          + "Canter on correct leads\n"
          + "Cross any obstacle\n"
          + "Stand tied with patience\n"
          + "Respect its handler/rider\n"
          + "Tolerate dogs, traffic, gun fire, and many other scary experiences.\n");
      statementLabel2.setTextAlignment(TextAlignment.CENTER);
      VBox statementContainer = new VBox(15, statementLabel1, separator, statementLabel2);
      statementContainer.setStyle("-fx-background-color: lightgrey;");
      statementContainer.setPadding(new Insets(25));
      statementContainer.setAlignment(Pos.CENTER);

      // Display additional important information and links
      Image infoImage1 = new Image("file:./res/facebook.png");
      ImageView infoImageView1 = new ImageView(infoImage1);
      infoImageView1.setFitHeight(65);
      infoImageView1.setPreserveRatio(true);
      Label facebookLabel = new Label("Follow Us On Facebook");
      facebookLabel.setTextAlignment(TextAlignment.CENTER);
      Label facebokSubLabel = new Label("Stay updated on what's happening with us and our horses.");
      facebokSubLabel.setMaxWidth(300);
      facebokSubLabel.setTextAlignment(TextAlignment.CENTER);
      facebokSubLabel.setWrapText(true);
      Button facebookButton = new Button("Learn More");
      VBox info1 = new VBox(15, infoImageView1, facebookLabel, facebokSubLabel, facebookButton);
      info1.setAlignment(Pos.CENTER);
      facebookButton.setOnAction( event -> {
        VBox facebookContainer = new VBox(facebookPage());
        facebookContainer.getStyleClass().add("page-vbox");
        VBox.setVgrow(root, Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(facebookContainer);
          scrollPane.setFitToWidth(true);
          root.setCenter(scrollPane);
          scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

          primaryStage.setFullScreen(true);
          primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
          primaryStage.setResizable(true); 
      });

      Image infoImage2 = new Image("file:./res/tips.png");
      ImageView infoImageView2 = new ImageView(infoImage2);
      infoImageView2.setFitHeight(65);
      infoImageView2.setPreserveRatio(true);
      Label tipsLabel = new Label("Tips and Advice");
      tipsLabel.setTextAlignment(TextAlignment.CENTER);
      Label tipsSubLabel = new Label("Read some of the best advice complied over the years from experience for first-time horse buyers.");
      tipsSubLabel.setMaxWidth(300);
      tipsSubLabel.setTextAlignment(TextAlignment.CENTER);
      tipsSubLabel.setWrapText(true);
      Button tipsButton = new Button("Learn More");
      VBox info2 = new VBox(15, infoImageView2, tipsLabel, tipsSubLabel, tipsButton);
      info2.setAlignment(Pos.CENTER);
      // add action to button
      tipsButton.setOnAction( event -> {
        VBox tipsContainer = new VBox(tipsAndAdvicePage());
        tipsContainer.getStyleClass().add("page-vbox");

        ScrollPane scrollPane = new ScrollPane(tipsContainer);
          scrollPane.setFitToWidth(true);
          root.setCenter(scrollPane);
          scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

          primaryStage.setFullScreen(true);
          primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
          primaryStage.setResizable(true); 
      });

      Image infoImage3 = new Image("file:./res/information.png");
      ImageView infoImageView3 = new ImageView(infoImage3);
      infoImageView3.setFitHeight(65);
      infoImageView3.setPreserveRatio(true);
      Label breedsLabel = new Label("Breed Information");
      breedsLabel.setTextAlignment(TextAlignment.CENTER);
      Label breedsSubLabel = new Label("Learn some general information about the Tennessee Walking Horse Breed.");
      breedsSubLabel.setMaxWidth(300);
      breedsSubLabel.setTextAlignment(TextAlignment.CENTER);
      breedsSubLabel.setWrapText(true);
      Button breedsButton = new Button("Learn More");
      VBox info3 = new VBox(15, infoImageView3, breedsLabel, breedsSubLabel, breedsButton);
      info3.setAlignment(Pos.CENTER);
      // add action to button
      breedsButton.setOnAction( event -> {
        VBox breedInfoContainer = new VBox(breedInfoPage());
        breedInfoContainer.getStyleClass().add("page-vbox");
        VBox.setVgrow(root, Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(breedInfoContainer);
          scrollPane.setFitToWidth(true);
          root.setCenter(scrollPane);
          scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

          primaryStage.setFullScreen(true);
          primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
          primaryStage.setResizable(true); 
      });

      HBox infoContainer = new HBox(100, info1, info2, info3);
      infoContainer.setPadding(new Insets(15));
      infoContainer.setAlignment(Pos.CENTER);

      // For information at the bottom of page
      Label contactTitle = new Label("Contact Us For Inquiries:\n"
                + "LKidder328@aol.com\n"
                + "281-726-4545");
      Label address = new Label("Cloud 9 Walkers\n"
                + "P. O. Box 878\n"
                + "Hardin, Texas 77561-0878");
      HBox contactContainer = new HBox(20, contactTitle, address);
      contactContainer.getStyleClass().add("info-section");
      contactContainer.setPadding(new Insets(10));

      // Handle About menu click
      aboutMenuLabel.setOnMouseClicked(event -> {
          VBox aboutMenuContent = new VBox(aboutPage());
          aboutMenuContent.getStyleClass().add("page-vbox");
          VBox.setVgrow(root, Priority.ALWAYS);

          ScrollPane scrollPane = new ScrollPane(aboutMenuContent);
          scrollPane.setFitToWidth(true);
          root.setCenter(scrollPane);
          scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

          primaryStage.setFullScreen(true);
          primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
          primaryStage.setResizable(true); 
      });

      // Handle For Sale menu's menu item clicks
      browseForSaleMenuItem1.setOnAction(event -> {
        VBox horsesPageContainer = new VBox(horsesPage());
        horsesPageContainer.getStyleClass().add("page-vbox");

        ScrollPane scrollPane = new ScrollPane(horsesPageContainer);
          scrollPane.setFitToWidth(true);
          root.setCenter(scrollPane);
          scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

          primaryStage.setFullScreen(true);
          primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
          primaryStage.setResizable(true); 
      });
      browseForSaleMenuItem2.setOnAction(event -> {
        VBox foalsPageContainer = new VBox(foalsPage());
        foalsPageContainer.getStyleClass().add("page-vbox");

        ScrollPane scrollPane = new ScrollPane(foalsPageContainer);
          scrollPane.setFitToWidth(true);
          root.setCenter(scrollPane);
          scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

          primaryStage.setFullScreen(true);
          primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
          primaryStage.setResizable(true); 
      });

      // Handle Stories menu click
      storiesMenuLabel.setOnMouseClicked(event -> {
          VBox storiesMenuContent = new VBox(stories());
          storiesMenuContent.getStyleClass().add("page-vbox");

          ScrollPane scrollPane = new ScrollPane(storiesMenuContent);
          scrollPane.setFitToWidth(true);
          root.setCenter(scrollPane);
          scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

          primaryStage.setFullScreen(true);
          primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
          primaryStage.setResizable(true); 
      });

      // Handle Home menu click
      homeMenuLabel.setOnMouseClicked(event -> {
        Scene scene = new Scene(sceneOne(primaryStage));
        primaryStage.setScene(scene);
        scene.getStylesheets().add("mystyle.css");
      primaryStage.setFullScreen(true);
      primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
      primaryStage.setResizable(true);
      });

      VBox vbox = new VBox(50, root, introContainer, forSalePreviewContainer, statementContainer, infoContainer, contactContainer);

      // For scrolling
      ScrollPane scrollPane = new ScrollPane(vbox);
      scrollPane.setFitToWidth(true);
      root.setCenter(scrollPane);
      scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

      VBox contentVbox = new VBox(root, vbox);

      return contentVbox;
    }

    public VBox facebookPage() {
      Label title = new Label("Our Facebook Information");
      title.setStyle("-fx-font-size: 50 pt;");
      Label fbInfo1 = new Label("See our day-to-day activities, entertaining pictures, videos as well as training, riding and general care tips.");
      Label fbInfo2 = new Label("Search \"Cloud 9 Walkers\" in the Facebook app");
      Label fbInfo3 = new Label("or");
      Label fbInfo4= new Label("Check out this link to view our page: https://www.facebook.com/p/Cloud-9-Walkers-100057184131412/");
      fbInfo1.setStyle("-fx-font-size: 22 pt;");
      fbInfo2.setStyle("-fx-font-size: 18 pt;");
      fbInfo3.setStyle("-fx-font-size: 18 pt;");
      fbInfo4.setStyle("-fx-font-size: 18 pt;");
      VBox text = new VBox(10, fbInfo2, fbInfo3, fbInfo4);
      text.setAlignment(Pos.CENTER);
      VBox contentVbox = new VBox(40, title, fbInfo1, text);
      contentVbox.setAlignment(Pos.CENTER);
      contentVbox.setPadding(new Insets(50));
        return contentVbox;
    }

    public VBox tipsAndAdvicePage() {
      Text title = new Text("Most Requested Pleasure Tips by Laura Kidder");
      title.setStyle("-fx-font-size: 50 pt;");
      Text tipsAndAdvice = new Text("1. Do not tie your horse with a long lead.  I see this all the time where “considerate” owners tie their horses on a long line to the side of their trailer or a fence so that their horses can graze.  Your horse should not be able to lower its head to a position where it can get a foot over the rope.  I personally saw a horse which was tied this way get his foot around and over the rope, panic, and literally “saw” his foot into such an injury that he was rendered lame for life.  Each time anyone would approach him to untangle him, he would panic again, causing deeper cuts.\n"
          + "\n2. Not in a trailer, either!  You will do your horse a favor to tie him short enough so that he cannot turn his head more than ninety degrees either way.  If he gets his head down and a foot over his lead, he can tear your trailer up while inflicting damage to himself, not to mention the difficulty in helping a panicking horse inside a trailer.\n"
          + "\n3. Do not leave halters on in the pasture or the stall.   You wouldn’t believe what a horse can get its halter hung on, including its own foot (while scratching its face).  Worst case scenario is death from a broken neck. \n"
          + "\n4. Breast straps are functional.  They’re not just for decoration.  Flopping, loose breast straps are not holding the saddle in place, so adjust the straps where the breast strap fits snugly so the saddle will not slide back. \n"
          + "\n5. Do not feed horses in a group.  No matter how much you think you know your horse, or how much you think your horse loves you, feed does to horses what money does to humans:  brings out the Neanderthal!  When you walk into a group (more than one) of horses with a feed bucket, a primeval instinct of survival kicks in, creating intense competition and when kicking at another horse to climb to the top of the hierarchy, a horse never checks to see that you’re not in his line of fire before declaring war.  I personally saw a friend of mine get her arm broken this way, moments after I warned her, even though she was sure her horses would never hurt her.  She was lucky it was just her arm and not her head! \n"
          + "\n6. Pasture horses are happier, easier to enjoy.  Horses don’t cause as much trouble and set up as many bad habits if they’re allowed to “be horses”.  If you want your horse to stay pretty all year, stall him out of the damaging rays of the sun during the daytime and allow him to graze at night.  He’ll stay glossy and dark, but mentally healthier and happier. \n"
          + "\n7. Stay out of the horse’s mouth.  When jumping obstacles, riders frequently unintentionally snatch their horse’s mouth for balance as they land.  You can avoid this if you will take the hand you are holding the reins in (both hands if applicable) and right before the horse jumps, grab a firm handful of his mane about halfway up his neck (toward his ears) with the reins locked in with his mane.  Release after you land, and there is absolutely no way you can hurt him if you follow this procedure. \n"
          + "\n8. Compromise.  Forward motion avoids trouble.  Try to control your panic if your horse is edgy and trying to move around against your will.  If he’s that agitated, you don’t want to set up a confrontation with him, but you don’t want to let him get away with disobedience either, so simply let him walk, but control where he is walking.  As long as you are moving forward, your horse is least likely to rear or throw a fit and fall down on you.  Make no mistake – a horse will hurt himself while throwing a fit, and in the process, you may be injured.  Horses are not intelligent enough to realize that their tantrums may result in their suffering.  Both you and your horse come to a compromise – he wants to move and you want him to stand still.  Let him move, but you control where he moves.\n"
          + "\n9. Check and release.  Try not to hang on his mouth to slow or stop him.  This will develop a hard, unresponsive mouth.  Pull quickly and firmly (check) until you feel at least a change in speed, then immediately release.  He might not stop, but don’t stay in his mouth until he does.  He needs to receive a reward for responding, even though it wasn’t the exact response you were looking for.  The release is his reward.  Then check again and repeat until he slows to the speed you want or until he halts, if you want a halt. \n"
          + "\n10. Never say “whoa” unless you mean it.  To slow down, talk to him in soothing tones and use a different verbal command than your command for halting, such as “easy” or “walk”.  When you do say “whoa”, MAKE HIM WHOA!! \n"
          + "\n11. Don’t dismount immediately after riding.  When you return to the barn (or trailer) after a ride, spend time just sitting on your horse to discipline him in patience, which will actually help prevent him from becoming barnsour. \n"
          + "\n12. Don’t take off immediately after mounting.  Practice sitting still, or if he’s too frisky to stand completely still, practice moving but not going anywhere.  He will soon learn that your mounting him does not mean you’re going to take off immediately, and he will eventually learn to stand completely still while you mount. \n"
          + "\n13. Cross ditches at an angle to avoid jumping.  Slow down before you get to your obstacle and check, release to avoid getting up momentum to jump.  In competitive riding, you are penalized if your horse rushes down or up an incline because a true pleasure horse works relaxed and on a loose rein.  Practice stopping and standing in the middle of an obstacle, such as a ditch, to dissuade jumping in the long term. \n"
          + "\n14. Before returning to the barn, head back out.  If you ride your horse at home, he will frequently develop a habit of “barn-sourness”, which is quite unpleasant.  Instead of returning from a ride and going straight to the barn to unsaddle, make a 180 and head back out again, or completely bypass the entrance to your property two or three times.  Then, when you do get back to the barn, sit on him for awhile before dismounting. \n"
          + "\n15. Safety tip:  Wait until everyone is mounted before taking off.  Horses are naturally herd-bound creatures, and it’s a rare creature that will stand perfectly still and wait for you to get completely in the saddle while all his buddies are leaving him. \n"
          + "\n16. Let your horse move naturally.  Once again, don’t hang onto the reins.  You are riding a gaited horse, which moves faster than stock breeds, so let him move out freely in his flat or running walk at the beginning of a ride.  If he’s of a more spirited nature, he will resent being suppressed to a “dog walk” and trying to suppress him at the start of a ride may cause him to be fractious.  Let him wear off some steam before you start demanding precision. \n"
          + "\n17. Safety tip regarding child riders:  If your horse is inclined to toss his nose and a child is going to be riding him, put a tie-down on him.  If a horse is allowed to toss his nose, he can sometimes flip his bit, and should he decide to run with the bit upside down, a child cannot stop him.  An adult friend of mine recently experienced this and now walks with a steel rod in his leg.  Some people erroneously believe Walking Horses aren’t supposed to wear tie-downs.  (Some also believe Walking Horses aren’t supposed to canter!)  Check your old Voice Magazine copies – they had an excellent article on how to use tie-downs awhile back.\n"
          + "\n18. Check tack before riding.  Be sure nothing is flopping to alarm your horse, and please make sure your bit is not hanging halfway out of your horse’s mouth or clanging against his teeth, my personal pet peeve.  Most bits should be fitted with a wrinkle or two in the corners of your horse’s mouth.  If you use a cotton girth, be sure it is clean or it will hold sand and work very much like sandpaper, causing girth galls. \n"
          + "\n19. Inspect “boogers”.  If your horse shies at an obstacle, be patient and insist that he walk completely up to the offending obstacle (i.e., trash cans, mailboxes, signs, etc.).  It may take many minutes to finally convince him to touch it with his nose, and you may actually have to lead him up to it, but it will pay off eventually.  I had a mare once that would automatically proceed toward whatever spooked her because she knew she would be asked, and eventually she quit shying altogether because she learned to trust me.  \n"
          + "\n20. Be trustworthy.  Never, ever make your horse inspect anything that will hurt or reasonably scare him.  Also, never expose him to dangerous situations and you will earn his trust.  This policy will pay great dividends, because as he learns to trust you, he will not have a reason to refuse anything you ask of him, and eventually, he won’t! \n"
          + "\n21. Never completely trust a stallion!  While a lot of Tennessee Walking Horse stallions are remarkably docile and even quite loveable, they are driven (and ruled) by a hormonal urge to reproduce, and their instinct to be territorial can be dangerous if you should lower your guard and get caught in the crossfire!  I used to ride a stallion which was exceptionally quiet and docile, but every once in a while, a certain gelding would set his temper off, and he would charge them, even with me in the saddle.  Always err on the side of caution and never drop your guard with a stallion, no matter how well you think you know him. \n"
          + "\n22. Wear safety gear!  Although I frequently don't practice what I preach (I've been known to ride barefooted and bareback quite often!), I do believe boots are mandatory around horses.  I have had horses with shoes on step on my bare foot and then twist and turn on it.  Also, a bare foot will go right through a stirrup and if your horse should ever get spooked and catch you off guard, bolting and running, you might be dragged from the stirrup.  A young girl in East Texas died this way in 2002, so it does happen.  When placing your foot in the stirrup, be sure to have the ball of your feet ONLY on the stirrup.  Don't have your arches in the stirrup -- that's too far in.  An approved safety helmet is a good idea, too.  These precautions are recommendations just as seat belts are in a car. \n"
          + "\n23. Never tie yourself to your horse.  This may sound funny, but I have seen people lead their horse through their suburban neighborhood for exercise, and they are tempted to tie the lead rope around their waist or wrap it a couple of times around their shoulder.  A friend of mine did this while she was lunging her colt.  She wrapped the excess line around her shoulder a couple of times, and when the neighbor cranked a loud engine, the colt bolted, which jerked the owner down, dislocated her shoulder and broke her collar bone.  \n"
          + "\n24. Arena Riding:  When you ride in the arena, try not to stop your horse at the gate, or even at the same place each time you finish riding and dismount.  Riders who make it a habit to dismount at the same place each time give their horse an opportunity to set up 'sour' behavior, and your horse may begin to exhibit 'ring sourness', where he misbehaves, dances around or throws a fit each time he reaches the gate, or the \"finish line\", in his mind.  Try dismounting in the center of the ring, getting off and leading your horse out, then the next time, on the south side, next time, north side, etc.\n"
          + "\n25. Always lead with a lead rope:  When moving your horse around, even if it is just across the hall from one stall to another, always use a lead rope.  Anything could happen, and for example, if your horse were to get spooked, he could lunge back and cause injury (such as dislocated shoulders, rope burns or in one case that I heard of, amputated fingers!). \n"
          + "\n26. Make your horse face you:  When you get ready to turn your horse loose, always make him turn and face you, unsnap the lead rope or take off the halter, and be prepared to get back out of the way FAST, because he may be feeling really good and happy to be free, anticipating his run around the pasture.  A lot of times, a horse will kick up with his hind feet as he runs off, and he can inadvertently kick you in the process. \n"
          + "\n27. No excess protein!!!!:  (I don't know if I can emphasize this strongly enough.)  Protein can turn your kitten of a horse into a mountain lion!  Most pleasure horses are only ridden once or twice weekly, and they just don't require 14% or 16% feed.  In fact, with a good pasture, they really don't require feed at all -- that's what God made grass for, and it's the healthiest diet for them, but who can resist giving them a scoop now and then?  If your horse does not have access to free exercise and roughage, then put him on a good 10% (no more than 12%) protein feed and all the good grass hay you care to afford him.  (Limit his alfalfa hay -- it's 18% and above in protein.)  \n"
          + "\n28. Avoid smooching your horse!:  So many people try to treat horses like dogs, and they want their horses to LOVE them.  That's a natural way to feel, but nature's balance requires Love and Fear.  Proper balance is called \"Respect\", and a horse who does not respect a person can never truly love them.  When a horse has proper respect, then he loves his owner properly.  Horses who are allowed to nuzzle are likely to graduate to nibbling, then nipping, and ultimately feeling the freedom and power to bite.  This can be dangerous, and no matter how much you believe your horse \"loves\" you, remember that he may not be aware of how much stronger he is than you are, and you can be hurt accidentally. \n");
      tipsAndAdvice.setStyle("-fx-font-size: 18 pt;");
      tipsAndAdvice.setWrappingWidth(1350);
      VBox contentVbox = new VBox(40, title, tipsAndAdvice);
      contentVbox.setAlignment(Pos.CENTER);
      contentVbox.setPadding(new Insets(50));
        return contentVbox;
    }

    public VBox breedInfoPage() {
      Text title = new Text("General Information on the Tennessee Walking Horse Breed");
      title.setStyle("-fx-font-size: 50 pt;");
      Text breedInfo = new Text("Looking for a particular breed of horse which is naturally docile, known for good, calm dispositions, smooth to ride and easy to handle?  All your research will point you to the Tennessee Walking Horse.  I get lots of calls from people who haven't had a horse in their lives since they were teens, or never had one at all, empty-nesters, people who have more time on their hands due to a change in career, marital status, family status, and/or even health status!  Doctors who have any horse knowledge at all consistently refer people with back problems (who don't want to give up their riding) to gaited horses, especially the Tennessee Walking Horses.\n"
          + "\nImagine having the sweetest-natured horse, a boost for your confidence, and at the same time, never bouncing in the saddle!  No posting, no jarring -- just \"smooth sailing\". \n"
          + "\nThe Tennessee Walking Horse is renowned for his ability to perform a smooth, 4-beat lateral gait, similar to other breeds of horses, except more quickly timed, and they're BORN that way!  Yes, the first steps they take are \"gaited steps\" -- no training or devices needed.  The Tennessee Walking Horse performs three distinct gaits: the flat foot walk, running walk, and canter.  The horse glides over the track left by the front foot with the hind foot, which is known as 'overstride'.  A true-gaited Tennessee Walker will nod his head in rhythm with the cadence of his feet, and sometimes flop their ears with each nod.  Most walking horses are capable of performing a regular slow walk like any other breed, a \"flat walk\" (the first gait), a \"running walk\" (the 2nd gait, which can be as fast as 10 mph) and then the canter.  Walking horses are also known for their beautiful 'carousel' canters, rising and falling slowing and smoothly.  Other gaits that can be performed by some Walkers are the pace (which is undesirable because it's rough), the rack, the foxtrot, and the single-foot.\n"
          + "\nThis light breed of pleasure horse was bred to work in the fields during the week, especially to ride the turn rows on the plantations in the south, but be a smooth ride on the weekends for pleasure.  A fusion of Thoroughbreds, Canadian Pacers, Saddlebreds, Morgans, American Standardbreds and Narragansett Pacers, this docile servant evolved in the middle Tennessee bluegrass region, and therefore christened the \"Tennessee Walking Horse\".  The Tennessee Walker's size ranges from 14H to 17H, with the average being from 15H to 15.2H.  From the 1930's, this gentle breed was known for having rather large, coarse heads, but through the years, especially due to the influence of \"Pride of Midnight\", a son of the 1945, 1946 World Grand Champion \"Midnight Sun\".  We believe the beautiful head began with him, and most Walkers today are much more refined and pretty than their ancestors.  Colors are almost limited to your imagination!  Walkers are black, sorrel, chestnut, grey, roan, bay, buckskin, champagne, palomino, cremello and spotted (sabino, tobiano and overo).\n"
          + "\nA common ridiculous notion held by many people not familiar with this gentle breed is that they do not run, or if you do run them, you \"mess up\" their gaits.  NOT TRUE!  (Watch the old Roy Rogers movies!) We show these versatile horses in English pleasure classes, Western pleasure classes, basic reining, trail obstacle, driving, fences, water glass (a fun class to show off how smooth your horse is), and even barrels and poles.  Now, if you're looking for a serious competitive barrel or pole horse, you're not going to buy a Tennessee Walker -- they're bred for pleasure, but that's not to say they cannot compete.  They are simply not bred for bursts of speed, but they are capable of running the patterns and running them fast.  We have a National Versatility Program (www.twhbea.com for more information) in which we compete in a wide range of these types of classes, showcasing the breed's versatility.\n"
          + "\nYou're not going to buy a Walking Horse for rodeo competition, roping calves, cutting or running steers, but that's not to say that they cannot do it! \n"
          + "\nYou're going to buy a Tennessee Walking Horse to fall in love, to have a furry, loveable equine friend, to become addicted to the glide ride, experiencing the controlled power of a horse in ultimate comfort, and best of all, you'll buy a Tennessee Walking horse for much-needed stress relief therapy!\n");
      breedInfo.setStyle("-fx-font-size: 18 pt;");
      breedInfo.setWrappingWidth(1350);
      VBox contentVbox = new VBox(40, title, breedInfo);
      contentVbox.setAlignment(Pos.CENTER);
      contentVbox.setPadding(new Insets(50));
        return contentVbox;
    }

    public VBox stories() {
      Text title = new Text("Just Plain Sunshine by Laura Kidder");
      Text story1 = new Text("I love big, stocky gaited horses with wide chests, butts bigger than mine, beautiful heads, classy animals, and since I am more than cognizant that life is short, I have learned to be a little \"short\" on shyness. When I see the brass ring, I just reach out and\n"
          + "try to grab it, and on a recent beautiful Kentucky trail ride, I spotted a jewel of a brass ring . . .\n"
          + "\n"
          + "In May of 2009, I went riding with a group of folks led by an older gentleman who reminded me of the singer Kenny Rogers, with his handsome salt and pepper hair and beard. He was riding one of the most beautiful, stocky, big jet black Tennessee Walking\n"
          + "horse geldings that I'd seen in a long time, and I rode up next to him to ask if his horse was for sale, instinctively knowing that it was his pride and joy. He smiled a big, wide smile and told me that although they had three more horses at home, this one was his wife's, Laverne's, favorite. Even though she never rode, she had threatened that she'd sell HIM before that horse would ever leave the family. I spent the next five minutes trying to convince him that it was just a horse and Laverne would forgive him, but he was firm in his rejection of my offer, although he couldn't help but laugh at my shameless begging.\n"
          + "\n"
          + "We were riding on the side of a mountain in eastern Kentucky in late spring, and the cool air was thick with humidity from the previous three days of rain. The mist which seemed to rise from the ground made our small group of seven riders feel like we were in our own quiet, private world, with only an occasional bird chirping, the clop of the horses' hooves on the trail, and the soft patter of light rain falling on the trees in the forest surrounding us.\n"
          + "\n"
          + "I remarked to my new trail riding friend that I felt sorry for the other riders back at camp who opted out of the day's ride because of the weather. Neither of us could believe that out of a couple of thousand people, only seven of us understood how comfortable a raincoat was or how short life is. He agreed with me, and we continued to ride along silently, listening to the hoof beats, softly squeaking leather, and inhaling the wonderful smell from the warm steam rising off our horses. I thought that riding in the rain was like being an honorary member in an exclusive club, where you are treated to sights, sounds and smells that some people never get to experience.\n"
          + "\n"
          + "We came upon a cave on the side of the mountain that looked like a good place to take a lunch break, so we tied our horses to trees and climbed up a few feet to get out of the misting rain. Some had brought hot coffee with them and the smell of it was so incredibly alluring that I would have been afraid to taste it, lest my enjoyment be dulled by reality! I had packed ham and cheese sandwiches with plenty of extras in case someone hadn't brought their lunch, so I passed them out to grateful takers, and I laughed at one rider who declined in favor of his canned potted meat and crackers. As a child, I had gotten sick on that little delicacy because we ate it so often (for it was all that we could afford sometimes) and to this day, I can't even stand the thought of eating such \"dog food\", but to him, it was as good as a home cooked meal, or at least he assured me that it was.\n"
          + "\n"
          + "When we got back on the trail again, I pestered Kenny Rogers about his horse again, and came up with several creative scenarios for him to get forgiveness for selling his horse, such as telling his wife that a bear attacked and took the horse, but spared her husband's life. Wouldn't she be so grateful that he survived such a calamity? He looked at me sideways and told me that she'd never forgive him for not throwing himself in front of the bear for the horse's sake!\n"
          + "\n"
          + "As we rode on, he began to share a story with me that made this experience very poignant . . .\n"
          + "\n"
          + "\"I am riding today, in the rain, specifically, in honor of my friend, Gus. We spent our childhoods as best friends, went to school together, graduated, joined the military and had families. For the first 60 years of my life, he was like a brother to me, and many, many days we spent just like this, riding in the mountains on our horses with our dogs,\" Kenny reminisced. \"I can't tell you how many times I wanted to sleep in on cold or rainymornings, just stay in the house, but Gus would come driving up with his horse and his dog, knocking on the door and asking me if I as going to sleep all day! He believed that you should register the opposite of whatever the weather man said – if he said 60% chance of rain, you should only hear '40% chance of sunshine'. He told me over and over again that life was just too short to pass up any opportunities or to be sedentary. He could be annoying when I was feeling lazy, and sometimes I dreaded days like this, when I'd rather just rest up, but I can't begin to count the fun times we had as a result of his pushing me to 'enjoy this short life.'\"\n"
          + "\n"
          + "Kenny went on, \"There was nothing that we couldn't tell each other, and I considered Gus to be part of my family. He never had children and a few years ago, his wife passed away, leaving him alone. When he began to have heart trouble, I was worried that riding horses and taking camping trips would be too stressful for him, but I continued to ride with him, just like old times. After he landed in the hospital with a major heart attack, I decided that I didn't want to be responsible for him dying out in the woods on the side of a mountain, too far away from hospitals to save him, so I declined his invitations, requests and pleas to ride and camp with him.\" Kenny seemed to be almost in tears at this point, and he paused for awhile.\n"
          + "\n"
          + "\"Gus hated the hospital and desperately didn't want to die alone\", Kenny remembered out loud. \"He told me that if his time were to come, he'd be happiest to die on the side of a mountain with his best friend nearby, watching the rain spatter on the leaves and smelling the sweet sweat of his horse, soaking up all of God's glory than to be imprisoned in a hospital bed with no dignity. He begged me one last time to spend the weekend camping with him, like the old days, . . . two old life-long friends, the great outdoors, the mountain, the horses, the dogs . . . and foolishly, I told him no. I was afraid he'd die out there, and I couldn't bear to be responsible for it.\"\n"
          + "I could barely hear Kenny as he finished his story with his eyes downcast, staring hard at the trail in front of his horse as we clopped along. I could see his throat tightening and knew that he was struggling for understanding, forgiveness, feeling immense guilt. \"Gus never got to ride again because he wouldn't go by himself,\" Kenny continued, \"and he did have another heart attack. This time, he was at the grocery store, by himself, and when he fell to the floor, an ambulance was called. Before anyone could call me, he was rushed to the emergency room, where he couldn't be saved. He died alone, in the hospital, exactly as he feared.\"\n"
          + "Kenny got quiet again, and I waited. \"I'd give ten years of my life to have another chance to take him riding now,\" Gus' loyal\n"
          + "friend lamented. By now, I could tell that Kenny had been carrying this guilt with him for some time, and I tried to think of the right thing to say, but as a cancer survivor, I actually sympathized with Gus more, and would have wanted to take my chances on that mountain, as well. I struggled to find the right words, and finally said, \"You were a good friend to Gus, and he knew you were being protective of him. He probably would have done the same for you. I'm sure he remembered all the good times you had together and those memories eased his sorrows. I am just as sure that he's probably smiling at you right now, saying, 'Kenny, my precious friend, thanks for dedicating this beautiful, muddy, rainy trail ride to me . . . I'm enjoying it as much as you are, and I'm with you in your heart.'\"\n"
          + "\n"
          + "\"'Oh, and one more thing . . . sell that poor girl your big horse so she'll stop whining. Laverne will forgive you!'\"\n"
          + "\n"
          + "Kenny's eyes were shining and he threw his head back and laughed. \"Are you sure you didn't know my friend, Gus? That sounds just like something he'd say!\"\n"
          + "\n"
          + "We rode for seven damp, cool, wonderful hours that day and after we made our way back to camp, Kenny shook my hand, thanked me for listening, and one last time, found himself having to reject my 40th offer to buy his horse. He left smiling, and reminded me, \"Now, remember the \"Life-Is-Short-Forecast\": When the weather man says 60% chance of rain, you hear \"40% chance of sunshine, for, some of the best memories in your lifetime can happen in the rain, and then, sometimes, in spite of the odds, there's just plain sunshine.");
      title.setStyle("-fx-font-size: 50 pt;");
      story1.setStyle("-fx-font-size: 18 pt;");
      story1.setWrappingWidth(1350);


      VBox contentVbox = new VBox(30, title, story1);
      contentVbox.setPadding(new Insets(50));
      contentVbox.setAlignment(Pos.CENTER);

      return contentVbox;
    }

    public VBox aboutPage() {
      Text title = new Text("Mountain: My First Horse");
      Text aboutMe = new Text("Since I was 3 years old and can remember anything in my life, I have been head over heels in love with horses.  I loved their eyes, their soft noses, the noises they made, and especially they way they smelled!  I had the whole Breyer model collection, all the Walter Farley books, Chincoteague Island Pony, horse encyclopedias, anything horsey I could grab.  My grandfather bought ponies and donkeys for us to ride but we only got to enjoy that on occasional weekends.  I craved my own horse to groom, feed, ride, take care of and to love.  My parents divorced when I was 9 years old and with 2 younger brothers, Mom couldn’t afford to keep a horse, especially since we lived in the city and would have to board it. \n"
          + "\n"
          + "When I was 11 years old, I thought I was simply going to die if I didn’t get a horse.  I knew Mom couldn’t afford it, so I begged my father for a horse for Christmas.  I was making straight A’s in school, consistently babysat for spending money (which I saved toward an equine friend), and took care of my brothers after school while my mother worked.  (I needed a horse, for nothing less than sanity!)  My father told me no, he would not buy me a horse because in a couple of years, I would forget about them and be boy crazy.  (THAT never happened!)  Finally, my mother (who shared my love for horses) saved enough money to buy me my first horse –- a huge, raw-boned palomino 16H gelding (not gaited) appropriately named “Mountain”, which was supposed to be 7 years old.  She paid $200 for him and, of course, he turned out to be about 18 years old, but he was beautiful to me.  I rode him through all the neighborhoods bareback because I didn’t have a saddle.  I was quite a sight, as I had to carry a 5-gallon bucket around with me to be able to get back on him.  I landed a full-time babysitting job at the ripe old age of 11 for a graveyard shift-working single mother, who would bring her child to me at 9 every night and pick him up the next morning at 6.  This income paid for Mountain’s board, feed, shoeing and vet care, and I walked about a mile every morning before school and after school to feed him.  I even rode him in the Christmas parades bareback, and it was a 3-mile ride in the dark to get him there. \n\nMountain was my best friend and I couldn’t wait to get out of school everyday to go see him.  When other girls were having slumber parties and going to movies, I was babysitting constantly, to earn money to spend more time with my horse.  I was not interested in boys, drugs or partying – only in the tall golden guy with the big, soft nose!");
      title.setStyle("-fx-font-size: 50 pt;");
      aboutMe.setStyle("-fx-font-size: 18 pt;");
      aboutMe.setWrappingWidth(1350);

      
      Image image = new Image("file:./res/mountain.jpeg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(340);
        imageView.setFitHeight(250);

      VBox contentVbox = new VBox(30, title, aboutMe, imageView); 
     
      contentVbox.setPadding(new Insets(50));
      contentVbox.setAlignment(Pos.CENTER);

      return contentVbox;
    }

    public VBox horsesPage() {
      final int NUM_OF_HORSES = 18;
        final int DISPLAY_SIZE = 250;

        Image horseImageArr[] = new Image[NUM_OF_HORSES];
        for (int i = 0; i < NUM_OF_HORSES; i++) {
          horseImageArr[i] = new Image("file:./res/horse" + (i + 1) + ".png");
        }

        ImageView horseImageViewArr[] = new ImageView[NUM_OF_HORSES];
        for (int i = 0; i < NUM_OF_HORSES; i++) {
          horseImageViewArr[i] = new ImageView(horseImageArr[i]);
          horseImageViewArr[i].setPreserveRatio(true);
          horseImageViewArr[i].setFitWidth(DISPLAY_SIZE);
        }

        Label horseLabelArr[] = { new Label("Diva - $8,500"), 
                                  new Label("Yoder - $8,750"),
                                  new Label("Zip - $6,500"),
                                  new Label("Alen - $15,000"),
                                  new Label("Sadie - $3,500"),
                                  new Label("Tomahawk - $15,000"),
                                  new Label("JJ - $8,500"),
                                  new Label("JLo - $12,500"),
                                  new Label("Pia - $6,500"),
                                  new Label("War Eagle - $8,500"),
                                  new Label("Bunny - $8,500"),
                                  new Label("Ernie - $6,500"),
                                  new Label("Lucas - $7,000"),
                                  new Label("Katie - $6,500"),
                                  new Label("Brook - $8,500"),
                                  new Label("LA - $4,850"),
                                  new Label("Smoking Gun - $6,500"),
                                  new Label("Flip My Coin - $3,950"),};

        for (int i = 0; i < NUM_OF_HORSES; i++) {
          horseLabelArr[i].setWrapText(true);
          horseLabelArr[i].setMaxWidth(DISPLAY_SIZE);
        }

        GridPane horsePage = new GridPane();
        horsePage.setAlignment(Pos.CENTER);
        horsePage.setPadding(new Insets(20));
        horsePage.setVgap(15);
        horsePage.setHgap(50);
        horsePage.add(horseImageViewArr[0], 0, 0);
        horsePage.add(horseImageViewArr[1], 1, 0);
        horsePage.add(horseImageViewArr[2], 2, 0);
        horsePage.add(horseImageViewArr[3], 3, 0);

        horsePage.add(horseImageViewArr[4], 0, 2);
        horsePage.add(horseImageViewArr[5], 1, 2);
        horsePage.add(horseImageViewArr[6], 2, 2);
        horsePage.add(horseImageViewArr[7], 3, 2);

        horsePage.add(horseImageViewArr[8], 0, 4);
        horsePage.add(horseImageViewArr[9], 1, 4);
        horsePage.add(horseImageViewArr[10], 2, 4);
        horsePage.add(horseImageViewArr[11], 3, 4);

        horsePage.add(horseImageViewArr[12], 0, 6);
        horsePage.add(horseImageViewArr[13], 1, 6);
        horsePage.add(horseImageViewArr[14], 2, 6);
        horsePage.add(horseImageViewArr[15], 3, 6);

        horsePage.add(horseImageViewArr[16], 0, 8);
        horsePage.add(horseImageViewArr[17], 1, 8);

        horsePage.add(horseLabelArr[0], 0, 1);
        horsePage.add(horseLabelArr[1], 1, 1);
        horsePage.add(horseLabelArr[2], 2, 1);
        horsePage.add(horseLabelArr[3], 3, 1);

        horsePage.add(horseLabelArr[4], 0, 3);
        horsePage.add(horseLabelArr[5], 1, 3);
        horsePage.add(horseLabelArr[6], 2, 3);
        horsePage.add(horseLabelArr[7], 3, 3);

        horsePage.add(horseLabelArr[8], 0, 5);
        horsePage.add(horseLabelArr[9], 1, 5);
        horsePage.add(horseLabelArr[10], 2, 5);
        horsePage.add(horseLabelArr[11], 3, 5);

        horsePage.add(horseLabelArr[12], 0, 7);
        horsePage.add(horseLabelArr[13], 1, 7);
        horsePage.add(horseLabelArr[14], 2, 7);
        horsePage.add(horseLabelArr[15], 3, 7);

        horsePage.add(horseLabelArr[16], 0, 9);
        horsePage.add(horseLabelArr[17], 1, 9);

        Label horseLabel1 = new Label("View Our Horses Below");
        horseLabel1.setStyle("-fx-font-size: 50 pt;");
        Label horseLabel2 = new Label("Contact Us For Inquiries at LKidder328@aol.com");
        horseLabel1.setPadding(new Insets(50, 0, 10, 0));
        horseLabel2.setPadding(new Insets(10, 0, 50, 0));

      VBox contentVbox = new VBox(horseLabel1, horseLabel2, horsePage);
      contentVbox.setAlignment(Pos.CENTER);
      contentVbox.setPadding(new Insets(50));
        return contentVbox;
    }

    public VBox foalsPage() {
      final int NUM_OF_BABIES = 12;
        final int DISPLAY_SIZE = 250;

        Image babyImageArr[] = new Image[NUM_OF_BABIES];
        for (int i = 0; i < NUM_OF_BABIES; i++) {
          babyImageArr[i] = new Image("file:./res/baby" + (i + 1) + ".png");
        }

        ImageView babyImageViewArr[] = new ImageView[NUM_OF_BABIES];
        for (int i = 0; i < NUM_OF_BABIES; i++) {
          babyImageViewArr[i] = new ImageView(babyImageArr[i]);
          babyImageViewArr[i].setPreserveRatio(true);
          babyImageViewArr[i].setFitWidth(DISPLAY_SIZE);
        }

        Label babyLabelArr[] = { new Label("Neon - $2,000"), 
                new Label("Jesse James - $2,000"),
                new Label("Profile - $2,000"),
                new Label("Harmony - $2,000"),
                new Label("Pearli - $3,500"),
                new Label("Charlie - $2,500"),
                new Label("Chanel - $2,000"),
                new Label("Treasure - $2,000"),
                new Label("Chisum - $4,500"), 
                new Label("Gunslinger - $2,000"),
                new Label("Arrow - $2,000"),
                new Label("Sawyer - $2,000") };

        for (int i = 0; i < NUM_OF_BABIES; i++) {
          babyLabelArr[i].setWrapText(true);
          babyLabelArr[i].setMaxWidth(DISPLAY_SIZE);
        }

        GridPane foalPage = new GridPane();
        foalPage.setAlignment(Pos.CENTER);
        foalPage.setPadding(new Insets(20));
        foalPage.setVgap(15);
        foalPage.setHgap(50);
        foalPage.add(babyImageViewArr[0], 0, 0);
        foalPage.add(babyImageViewArr[1], 1, 0);
        foalPage.add(babyImageViewArr[2], 2, 0);
        foalPage.add(babyImageViewArr[3], 3, 0);

        foalPage.add(babyImageViewArr[4], 0, 2);
        foalPage.add(babyImageViewArr[5], 1, 2);
        foalPage.add(babyImageViewArr[6], 2, 2);
        foalPage.add(babyImageViewArr[7], 3, 2);

        foalPage.add(babyImageViewArr[8], 0, 4);
        foalPage.add(babyImageViewArr[9], 1, 4);
        foalPage.add(babyImageViewArr[10], 2, 4);
        foalPage.add(babyImageViewArr[11], 3, 4);

        foalPage.add(babyLabelArr[0], 0, 1);
        foalPage.add(babyLabelArr[1], 1, 1);
        foalPage.add(babyLabelArr[2], 2, 1);
        foalPage.add(babyLabelArr[3], 3, 1);

        foalPage.add(babyLabelArr[4], 0, 3);
        foalPage.add(babyLabelArr[5], 1, 3);
        foalPage.add(babyLabelArr[6], 2, 3);
        foalPage.add(babyLabelArr[7], 3, 3);

        foalPage.add(babyLabelArr[8], 0, 5);
        foalPage.add(babyLabelArr[9], 1, 5);
        foalPage.add(babyLabelArr[10], 2, 5);
        foalPage.add(babyLabelArr[11], 3, 5);

        Label foalLabel1 = new Label("View Our Foals Below");
        foalLabel1.setStyle("-fx-font-size: 50 pt;");
        Label foalLabel2 = new Label("Contact Us For Inquiries at LKidder328@aol.com");
        foalLabel1.setPadding(new Insets(50, 0, 10, 0));
        foalLabel2.setPadding(new Insets(10, 0, 50, 0));

      VBox contentVbox = new VBox(foalLabel1, foalLabel2, foalPage);
      contentVbox.setAlignment(Pos.CENTER);
      contentVbox.setPadding(new Insets(50));
        return contentVbox;
    }

    // puts all horses for sale information into a GridPane
    void addHorsesInfo(ArrayList<GridPane> arr) {
      final int NUM_OF_HORSES = 18;
      final int DISPLAY_SIZE = 250;

      Image horseImageArr[] = new Image[NUM_OF_HORSES];
      for (int i = 0; i < NUM_OF_HORSES; i++) {
        horseImageArr[i] = new Image("file:./res/horse" + (i + 1) + ".png");
      }

      ImageView horseImageViewArr[] = new ImageView[NUM_OF_HORSES];
      for (int i = 0; i < NUM_OF_HORSES; i++) {
        horseImageViewArr[i] = new ImageView(horseImageArr[i]);
        horseImageViewArr[i].setPreserveRatio(true);
        horseImageViewArr[i].setFitWidth(DISPLAY_SIZE);
      }

      Label horseLabelArr[] = { new Label("Diva - $8,500"), 
                                new Label("Yoder - $8,750"),
                                new Label("Zip - $6,500"),
                                new Label("Alen - $15,000"),
                                new Label("Sadie - $3,500"),
                                new Label("Tomahawk - $15,000"),
                                new Label("JJ - $8,500"),
                                new Label("JLo - $12,500"),
                                new Label("Pia - $6,500"),
                                new Label("War Eagle - $8,500"),
                                new Label("Bunny - $8,500"),
                                new Label("Ernie - $6,500"),
                                new Label("Lucas - $7,000"),
                                new Label("Katie - $6,500"),
                                new Label("Brook - $8,500"),
                                new Label("LA - $4,850"),
                                new Label("Smoking Gun - $6,500"),
                                new Label("Flip My Coin - $3,950"),};

      for (int i = 0; i < NUM_OF_HORSES; i++) {
        horseLabelArr[i].setWrapText(true);
        horseLabelArr[i].setMaxWidth(DISPLAY_SIZE);
      }

      // 6 horses per page
      GridPane horsesPage1 = new GridPane();
      horsesPage1.setAlignment(Pos.CENTER);
      horsesPage1.setPadding(new Insets(20));
      horsesPage1.setVgap(15);
      horsesPage1.setHgap(50);
      horsesPage1.add(horseImageViewArr[0], 0, 0);
      horsesPage1.add(horseImageViewArr[1], 1, 0);
      horsesPage1.add(horseImageViewArr[2], 2, 0);
      horsesPage1.add(horseImageViewArr[3], 0, 2);
      horsesPage1.add(horseImageViewArr[4], 1, 2);
      horsesPage1.add(horseImageViewArr[5], 2, 2);

      horsesPage1.add(horseLabelArr[0], 0, 1);
      horsesPage1.add(horseLabelArr[1], 1, 1);
      horsesPage1.add(horseLabelArr[2], 2, 1);
      horsesPage1.add(horseLabelArr[3], 0, 3);
      horsesPage1.add(horseLabelArr[4], 1, 3);
      horsesPage1.add(horseLabelArr[5], 2, 3);

      GridPane horsesPage2 = new GridPane();
      horsesPage2.setAlignment(Pos.CENTER);
      horsesPage2.setPadding(new Insets(20));
      horsesPage2.setVgap(15);
      horsesPage2.setHgap(50);
      horsesPage2.add(horseImageViewArr[6], 0, 0);
      horsesPage2.add(horseImageViewArr[7], 1, 0);
      horsesPage2.add(horseImageViewArr[8], 2, 0);
      horsesPage2.add(horseImageViewArr[9], 0, 2);
      horsesPage2.add(horseImageViewArr[10], 1, 2);
      horsesPage2.add(horseImageViewArr[11], 2, 2);

      horsesPage2.add(horseLabelArr[6], 0, 1);
      horsesPage2.add(horseLabelArr[7], 1, 1);
      horsesPage2.add(horseLabelArr[8], 2, 1);
      horsesPage2.add(horseLabelArr[9], 0, 3);
      horsesPage2.add(horseLabelArr[10], 1, 3);
      horsesPage2.add(horseLabelArr[11], 2, 3);

      GridPane horsesPage3 = new GridPane();
      horsesPage3.setAlignment(Pos.CENTER);
      horsesPage3.setPadding(new Insets(20));
      horsesPage3.setVgap(15);
      horsesPage3.setHgap(50);
      horsesPage3.add(horseImageViewArr[12], 0, 0);
      horsesPage3.add(horseImageViewArr[13], 1, 0);
      horsesPage3.add(horseImageViewArr[14], 2, 0);
      horsesPage3.add(horseImageViewArr[15], 0, 2);
      horsesPage3.add(horseImageViewArr[16], 1, 2);
      horsesPage3.add(horseImageViewArr[17], 2, 2);

      horsesPage3.add(horseLabelArr[12], 0, 1);
      horsesPage3.add(horseLabelArr[13], 1, 1);
      horsesPage3.add(horseLabelArr[14], 2, 1);
      horsesPage3.add(horseLabelArr[15], 0, 3);
      horsesPage3.add(horseLabelArr[16], 1, 3);
      horsesPage3.add(horseLabelArr[17], 2, 3);

      // add all pages to an array list for easier control
      arr.add(horsesPage1);
      arr.add(horsesPage2);
      arr.add(horsesPage3);
    }

    // puts all baby horses for sale information into a GridPane
    void addBabiesInfo(ArrayList<GridPane> arr) {
      final int NUM_OF_BABIES = 12;
      final int DISPLAY_SIZE = 250;

      Image babyImageArr[] = new Image[NUM_OF_BABIES];
      for (int i = 0; i < NUM_OF_BABIES; i++) {
        babyImageArr[i] = new Image("file:./res/baby" + (i + 1) + ".png");
      }

      ImageView babyImageViewArr[] = new ImageView[NUM_OF_BABIES];
      for (int i = 0; i < NUM_OF_BABIES; i++) {
        babyImageViewArr[i] = new ImageView(babyImageArr[i]);
        babyImageViewArr[i].setPreserveRatio(true);
        babyImageViewArr[i].setFitWidth(DISPLAY_SIZE);
      }

      Label babyLabelArr[] = { new Label("Neon - $2,000"), 
                              new Label("Jesse James - $2,000"),
                              new Label("Profile - $2,000"),
                              new Label("Harmony - $2,000"),
                              new Label("Pearli - $3,500"),
                              new Label("Charlie - $2,500"),
                              new Label("Chanel - $2,000"),
                             new Label("Treasure - $2,000"),
                             new Label("Chisum - $4,500"), 
                             new Label("Gunslinger - $2,000"),
                             new Label("Arrow - $2,000"),
                             new Label("Sawyer - $2,000") };

      for (int i = 0; i < NUM_OF_BABIES; i++) {
        babyLabelArr[i].setWrapText(true);
        babyLabelArr[i].setMaxWidth(DISPLAY_SIZE);
      }

      // 6 foals per page
      GridPane babiesPage1 = new GridPane();
      babiesPage1.setAlignment(Pos.CENTER);
      babiesPage1.setPadding(new Insets(20));
      babiesPage1.setVgap(15);
      babiesPage1.setHgap(50);
      babiesPage1.add(babyImageViewArr[0], 0, 0);
      babiesPage1.add(babyImageViewArr[1], 1, 0);
      babiesPage1.add(babyImageViewArr[2], 2, 0);
      babiesPage1.add(babyImageViewArr[3], 0, 2);
      babiesPage1.add(babyImageViewArr[4], 1, 2);
      babiesPage1.add(babyImageViewArr[5], 2, 2);

      babiesPage1.add(babyLabelArr[0], 0, 1);
      babiesPage1.add(babyLabelArr[1], 1, 1);
      babiesPage1.add(babyLabelArr[2], 2, 1);
      babiesPage1.add(babyLabelArr[3], 0, 3);
      babiesPage1.add(babyLabelArr[4], 1, 3);
      babiesPage1.add(babyLabelArr[5], 2, 3);

      GridPane babiesPage2 = new GridPane();
      babiesPage2.setAlignment(Pos.CENTER);
      babiesPage2.setPadding(new Insets(20));
      babiesPage2.setVgap(15);
      babiesPage2.setHgap(50);
      babiesPage2.add(babyImageViewArr[6], 0, 0);
      babiesPage2.add(babyImageViewArr[7], 1, 0);
      babiesPage2.add(babyImageViewArr[8], 2, 0);
      babiesPage2.add(babyImageViewArr[9], 0, 2);
      babiesPage2.add(babyImageViewArr[10], 1, 2);
      babiesPage2.add(babyImageViewArr[11], 2, 2);

      babiesPage2.add(babyLabelArr[6], 0, 1);
      babiesPage2.add(babyLabelArr[7], 1, 1);
      babiesPage2.add(babyLabelArr[8], 2, 1);
      babiesPage2.add(babyLabelArr[9], 0, 3);
      babiesPage2.add(babyLabelArr[10], 1, 3);
      babiesPage2.add(babyLabelArr[11], 2, 3);

      // add all pages to an array list for easier control
      arr.add(babiesPage1);
      arr.add(babiesPage2);
    }
}
