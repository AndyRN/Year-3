<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="x" %>
<div class="container" style="margin-top: 20px;">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">       
            <h1>Medicine</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form method="post" action="<%=application.getContextPath()%>/Front?direction=Medicine&action=<x:out value="${Action}" />">                        
            <div class="form-group">
                <label for="id" class="form-label">ID</label>
                <input type="text" readonly="readonly" name="id" value="<x:out value="${Medicine.id}"/>" class="form-control"/>
            </div>
            <div class="form-group">
                <label for="name" class="form-label">Name</label>
                <input type="text" name="name" value="<x:out value="${Medicine.name}"/>" class="form-control"/>
            </div>
            <div class="form-group">
                <label for="cost" class="form-label">Cost</label>
                <input type="number" name="cost" value="<x:out value="${Medicine.cost}"/>" class="form-control"/>
            </div>
            <div class="form-group">
                <input type="submit" value="<x:out value="${Action}" />" class="btn btn-success" />
                <a href="<%=application.getContextPath()%>/Front?direction=Patient&action=Cancel">
                    <button type="button" class="btn btn-default">Cancel</button></a>
            </div>
            </form>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>