/**
 * Created by stephan on 20.03.16.
 */

$(function () {
   // VARIABLES =============================================================
   var TOKEN_KEY = "jwtToken"
   var $notLoggedIn = $("#notLoggedIn");
   var $loggedIn = $("#loggedIn").hide();
   var $response = $("#response");
   var $login = $("#login");
   var $userInfo = $("#userInfo").hide();
   console.log("======================================"+document.cookie.split("; ") + document.charset+"======================================");

   // FUNCTIONS =============================================================
   function getJwtToken() {
      return localStorage.getItem(TOKEN_KEY);
   }

   function setJwtToken(token) {
	   console.log("qqqqqqqqqqqq"+ localStorage.getItem(TOKEN_KEY));
      localStorage.setItem(TOKEN_KEY, token);
   }

   function removeJwtToken() {
      localStorage.removeItem(TOKEN_KEY);
   }

   function checkTokenValidity(token) {
      console.log(token);
      if (token) {
         const JWT = token;
         const jwtPayload = JSON.parse(window.atob(JWT.split('.')[1]))
         const expiresAt = jwtPayload.exp;
         console.log(expiresAt);
         var d1 = new Date(jwtPayload.exp * 1000);
         console.log(d1);
         var actualTimeIn = (expiresAt * 1000) - new Date().getTime();
         console.log("qqqqqqwqwqw111111111111"+ actualTimeIn)
         return (expiresAt * 1000) > new Date().getTime()
      }
      return false
    }

   function doLogin(loginData) {
      $.ajax({
         url: "/api/authenticate",
         type: "POST",
         data: JSON.stringify(loginData),
         contentType: "application/json; charset=utf-8",
         dataType: "json",
         success: function (data, textStatus, jqXHR) {
            setJwtToken(data.id_token);
            $login.hide();
            $notLoggedIn.hide();
            showTokenInformation()
            showUserInformation();
         },
         error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.status === 401) {
               $('#loginErrorModal')
                  .modal("show")
                  .find(".modal-body")
                  .empty()
                  .html("<p>" + jqXHR.responseJSON.message + "</p>");
            } else {
               throw new Error("an unexpected error occured: " + errorThrown);
            }
         }
      });
   }

   function doLogout() {
      removeJwtToken();
      $login.show();
      $userInfo
         .hide()
         .find("#userInfoBody").empty();
      $loggedIn
         .hide()
         .attr("title", "")
         .empty();
      $notLoggedIn.show();
   }

   function createAuthorizationTokenHeader() {
      var token = getJwtToken();
      if (token) {
         return {"JWT_TOKEN": "Bearer " + token};
      } else {
         return {};
      }
   }

   function showUserInformation() {
      $.ajax({
         url: "/api/user",
         type: "GET",
         contentType: "application/json; charset=utf-8",
         dataType: "json",
         headers: createAuthorizationTokenHeader(),
         success: function (data, textStatus, jqXHR) {
        	 
        	 console.log(JSON.stringify(data));
        	 
            var $userInfoBody = $userInfo.find("#userInfoBody");
            $userInfoBody.append($("<div>").text("loginName: " + data.data.loginName));
            $userInfoBody.append($("<div>").text("empName: " + data.data.empName));
            $userInfoBody.append($("<div>").text("birthday: " + data.data.birthday));
            $userInfoBody.append($("<div>").text("Email: " + data.data.email));

            var $authorityList = $("<ul>");
            if(data.data.roles!= null){
            	  data.data.roles.forEach(function (role) {
                      $authorityList.append($("<li>").text(role.identificationCode));
                   });
            }
            var $authorities = $("<div>").text("Authorities:");
            $authorities.append($authorityList);

            $userInfoBody.append($authorities);
            $userInfo.show();
         }
      });
   }

   function showTokenInformation() {
      $loggedIn
         .text("Token: " + getJwtToken())
         .attr("title", "Token: " + getJwtToken())
         .show();
   }

   function showResponse(statusCode, message) {
      $response
         .empty()
         .text(
            "status code: "
            + statusCode + "\n-------------------------\n"
            + (typeof message === "object" ? JSON.stringify(message) : message)
         );
   }

   // REGISTER EVENT LISTENERS =============================================================
   $("#loginForm").submit(function (event) {
      event.preventDefault();

      var $form = $(this);
      var formData = {
         username: $form.find('input[name="username"]').val(),
         password: $form.find('input[name="password"]').val()
      };

      doLogin(formData);
   });

   $("#logoutButton").click(doLogout);

   $("#loginUserServiceBtn").click(function () {
	      $.ajax({
	         url: "/student/find",
	         type: "GET",
	         data: {"id": 1},
	         contentType: "application/json; charset=utf-8",
	         dataType: "json",
	         headers: createAuthorizationTokenHeader(),
	         success: function (data, textStatus, jqXHR) {
	            showResponse(jqXHR.status, JSON.stringify(data));
	         },
	         error: function (jqXHR, textStatus, errorThrown) {
	            showResponse(jqXHR.status, jqXHR.responseJSON.message)
	         }
	      });
	   });

   $("#exampleServiceBtn").click(function () {
      $.ajax({
         url: "/api/person",
         type: "GET",
         contentType: "application/json; charset=utf-8",
         dataType: "json",
         headers: createAuthorizationTokenHeader(),
         success: function (data, textStatus, jqXHR) {
            showResponse(jqXHR.status, JSON.stringify(data));
         },
         error: function (jqXHR, textStatus, errorThrown) {
            showResponse(jqXHR.status, jqXHR.responseJSON.message)
         }
      });
   });

   $("#adminServiceBtn").click(function () {
      $.ajax({
         url: "/api/hiddenmessage",
         type: "GET",
         contentType: "application/json; charset=utf-8",
         dataType: "json",
         headers: createAuthorizationTokenHeader(),
         success: function (data, textStatus, jqXHR) {
            showResponse(jqXHR.status, data);
         },
         error: function (jqXHR, textStatus, errorThrown) {
            showResponse(jqXHR.status, jqXHR.responseJSON.message)
         }
      });
   });

   $loggedIn.click(function () {
      $loggedIn
         .toggleClass("text-hidden")
         .toggleClass("text-shown");
   });

   // INITIAL CALLS =============================================================
   var token = getJwtToken()
   const notExpires = checkTokenValidity(token);
   if (notExpires) {
      $login.hide();
      $notLoggedIn.hide();
      showTokenInformation();
      showUserInformation();
   } else{
      removeJwtToken();
      //window.location = "/index.html";
   }
});
