<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>AGU Abstracts</title>
        <style type="text/css">
          .scrollable{
            overflow: auto;
            width: 500px
            height: 100px
            border: 1px silver solid;
          }
          .scrollable select{
            border: none;
          }
        </style>
    </head>

    <body>
        <h1>AGU Abstracts for ${firstName} ${lastName}</h1>
        <h3>We have retrieved all AGU abstracts with a last name and first initial equivalent to yours. AGU's naming convention of 
first intial and last name can present some ambiguities. Help us disambiguate this data while linking your abstracts to datasets. You can use the first column of the table below to indicate if the abstract is indeed yours.</h3> 
        <form name="input" action="http://ec2-54-225-124-3.compute-1.amazonaws.com/process_form.php" method="post">
        <input type="hidden" name="login" value="${login}">
        <table border="1">
            <tr>
                <th>I'm one of the authors</th>
                <th>Abstract</th>
                <th>AGU Session</th>
                <th>AGU Meeting</th>
                <th>R2R Ship/Cruise</th>
	 	<th>ECHO Dataset</th>
		<th>GCMD Dataset</th>
            </tr>
            <c:forEach items="${aguData}" var="agu">
               <tr>
                   <td>
                      <input type="radio" name="${agu.getUri()}" value="yes">yes<br>
                      <input type="radio" name="${agu.getUri()}" value="no">no<br>
                      <input type="radio" name="${agu.getUri()}" value="null" checked>Not Answered
                   </td>
                   <td>${agu.getAbstract()}</td>
                   <td>${agu.getSession()}</td>
                   <td>${agu.getYear()} ${agu.getMeeting()}</td>
                   <td>
                      <div class="scrollable">
                      <select id="r2rList" name="r2r_${agu.getUri()}" style="width: 300px">
                        <c:forEach items="${r2rData}" var="r2r">
                          <option value="${r2r}">${r2r}</option>
                        </c:forEach>
                      </select>
                      </div>
                   </td>
                   <td>
                      <div class="scrollable">
                      <select id="echoList" name="echo_${agu.getUri()}" style="width: 300px">
                        <c:forEach items="${echoData}" var="echo">
                          <option value="${echo}">${echo}</option>
                        </c:forEach>
                      </select>
                      </div>
                   </td>
                   <td>
                      <div class="scrollable">
                      <select id="gcmdList" name="gcmd_${agu.getUri()}" style="width: 300px">
                        <c:forEach items="${gcmdData}" var="gcmd">
                          <option value="${gcmd}">${gcmd}</option>
                        </c:forEach>
                      </select>
                      </div>
                   </td>
               </tr>
            </c:forEach>          
       </table>
       <input type="submit">
       </form>
    </body>

</html>
