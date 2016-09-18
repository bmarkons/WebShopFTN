<%@page import="org.apache.commons.io.filefilter.DirectoryFileFilter"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="org.apache.commons.io.filefilter.RegexFileFilter"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Collection"%>
<!DOCTYPE html>
<html>
  <head>
   <meta charset="UTF-8">
     <title>Administrator login</title>

     <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/angular_material/1.1.0-rc2/angular-material.min.css">
       <link rel="stylesheet" href="node_modules/md-color-picker/dist/mdColorPicker.min.css" rel="stylesheet" />
       <link rel="stylesheet" href="node_modules/lf-ng-md-file-input/dist/lf-ng-md-file-input.css">
         <link rel="stylesheet" href="node_modules/angular-jk-rating-stars/dist/jk-rating-stars.css" rel="stylesheet" type="text/css" />
         <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
           <link rel="stylesheet" href="bower_components/angular-ui-tree/dist/angular-ui-tree.css">



             <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
               <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
               <script src ="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.min.js"></script>
               <script src ="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-route.min.js"></script>

               <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.min.js"></script>
               <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-aria.min.js"></script>
               <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-messages.min.js"></script>
               <script src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/t-114/svg-assets-cache.js"></script>
               <script src="http://ajax.googleapis.com/ajax/libs/angular_material/1.1.0-rc2/angular-material.js"></script>

               <script src="js/webShopApp.js"></script>
               <!-- controllers -->
               <%
               String controllersDir = request.getRealPath("/js/controllers");
               Collection<File> controllerFiles = FileUtils.listFiles(new File(controllersDir), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
               for(File file : controllerFiles){
                 %>
                 <script src="<%= new File(request.getRealPath("/")).toURI().relativize(file.toURI()).getPath() %>"></script>
                 <%
               }
               %>
               <!-- services -->
               <%
               String servicesDir = request.getRealPath("/js/services");
               Collection<File> serviceFiles = FileUtils.listFiles(new File(servicesDir), new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
               for(File file : serviceFiles){
                %>
                <script src="<%= new File(request.getRealPath("/")).toURI().relativize(file.toURI()).getPath() %>"></script>
                <%
              }
              %>

              <script src="node_modules/md-color-picker/node_modules/tinycolor2/dist/tinycolor-min.js"></script>
              <script src="node_modules/md-color-picker/dist/mdColorPicker.min.js"></script>
              <script src="node_modules/lf-ng-md-file-input/dist/lf-ng-md-file-input.js"></script>
              <script src="node_modules/angular-jk-rating-stars/dist/jk-rating-stars.js"></script>
              <script src="bower_components/angular-ui-tree/dist/angular-ui-tree.min.js"></script>
            </head>

            <body ng-app="webShopApp" ng-controller="MainController" flex layout="column">
             <md-toolbar class="md-theme-light">
              <div class="md-toolbar-tools">
               <md-button ng-click="toggleSideNav()" class="md-icon-button" aria-label="Menu">
                <md-tooltip>
                 View menu
               </md-tooltip>
               <md-icon>menu</md-icon>
             </md-button>
             <h2>
              <span>WebShop:{{user.type}}</span>
            </h2>
            <span flex></span>
            <md-button ng-show="user.type == 'buyer'" ng-href="/WebShop/webShop.jsp#/shoppingCart">
              <label>Shopping cart</label>
              <md-icon>shopping_cart</md-icon>
            </md-button>
            <md-button ng-href="/WebShop/webShop.jsp#/profile" aria-label="User account">
             <md-tooltip>
              Your profile
            </md-tooltip>
            <label>{{user.username}}</label>
            <md-icon>account_circle</md-icon>
          </md-button>
          <md-button ng-href="/WebShop/LoginServlet?logout=true" class="md-icon-button" aria-label="Log out">
           <md-tooltip>
            Log out
          </md-tooltip>
          <md-icon>exit_to_app</md-icon>
        </md-button>
      </div>
    </md-toolbar>
    <md-sidenav md-component-id="left"
      class="md-sidenav-left"
      >
      <md-toolbar class="md-theme-indigo">
        <div class="md-toolbar-tools">
         <md-button ng-click="toggleSideNav()" class="md-icon-button" aria-label="Menu">
          <md-tooltip>
           View menu
         </md-tooltip>
         <md-icon>menu</md-icon>
       </md-button>
       <h2>
        <span>Menu</span>
      </h2>
      <span flex></span>
      <md-button ng-click="toggleSideNav()" ng-href="/WebShop/webShop.jsp#/profile" aria-label="User account">
       <md-tooltip>
        Your profile
      </md-tooltip>
      <label>{{user.username}}</label>
      <md-icon>account_circle</md-icon>
    </md-button>
    <md-button ng-href="/WebShop/LoginServlet?logout=true" class="md-icon-button" aria-label="Log out">
     <md-tooltip>
      Log out
    </md-tooltip>
    <md-icon>exit_to_app</md-icon>
  </md-button>
</div>
</md-toolbar>
<md-content layout-padding>
  <md-button ng-show="user.type == 'admin'" ng-click='toggleSideNav()' ng-href="/WebShop/webShop.jsp#/users" aria-label="User account">
    <md-icon>people</md-icon>
    <label>Users</label>
  </md-button><br/>
  <md-button ng-click='toggleSideNav()' ng-href="/WebShop/webShop.jsp#/stores" aria-label="Stores">
    <md-icon>store</md-icon>
    <label ng-if="user.type == 'admin' || user.type == 'buyer'">Stores</label>
    <label ng-if="user.type == 'seller'">My Stores</label>
  </md-button><br/>
  <md-button ng-click='toggleSideNav()' ng-href="/WebShop/webShop.jsp#/products" aria-label="Products">
    <md-icon>card_giftcard</md-icon>
    <label>Products</label>
  </md-button><br/>
  <md-button ng-click='toggleSideNav()' ng-href="/WebShop/webShop.jsp#/purchases" aria-label="Purchases">
    <md-icon>assignment_turned_in</md-icon>
    <label ng-if="user.type == 'admin'">Purchases</label>
    <label ng-if="user.type == 'buyer'">My Purchases</label>
    <label ng-if="user.type == 'seller'">My Stores Purchases</label>
  </md-button><br/>
  <md-button ng-show="user.type == 'admin'" ng-click='toggleSideNav()' ng-href="/WebShop/webShop.jsp#/reviews" aria-label="Reviews">
    <md-icon>mode_comment</md-icon>
    <label>Reviews</label>
  </md-button><br/>
  <md-button ng-show="user.type == 'admin'" ng-click='toggleSideNav()' ng-href="/WebShop/webShop.jsp#/categories" aria-label="Categories">
    <md-icon>flag</md-icon>          	
    <label>Categories</label>
  </md-button><br/>
  <md-button ng-show="user.type == 'admin'" ng-click='toggleSideNav()' ng-href="/WebShop/webShop.jsp#/deliverers" aria-label="Deliverers">
    <md-icon>directions_car</md-icon>          			
    <label>Deliverers</label>
  </md-button><br/>
</md-content>
</md-sidenav>
<div flex layout="row" layout-align="center start" ng-view></div>
</body>
</html>