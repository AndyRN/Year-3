<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="x" %>
<div class="container" style="margin-top: 20px;">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <h1>Patient</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form method="post" action="<%=application.getContextPath()%>/Front?direction=Patient&action=<x:out value="${Action}" />">
                <div class="form-group">
                    <label for="id" class="form-label">ID</label>
                    <input type="text" readonly="readonly" name="id" value="<x:out value="${Patient.id}"/>" class="form-control"/>
                </div>
                <div class="form-group">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" name="name" value="<x:out value="${Patient.name}"/>" class="form-control"/>
                </div>
                <div class="form-group">
                    <input type="submit" value="<x:out value="${Action}" />" class="btn btn-success" />
                    <a href="<%=application.getContextPath()%>/Front?direction=Patient&action=Cancel">
                        <button type="button" class="btn btn-default">Cancel</button></a>
                </div>
            </form>
        </div>
    </div>
    <br>
    <br>
    <br>

    <% if (request.getAttribute("Bill") == null && request.getAttribute("Patient") != null) {%>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form method="post" action="<%=application.getContextPath()%>/Front?direction=Bill&action=Add&patient=${Patient.id}">
                <div class="form-group">
                    <label for="fee" class="form-label">Consultation Fee</label>
                    <input type="number" name="fee" value="" class="form-control"/>
                </div>
                <div class="form-group">
                    <input type="submit" value="Add Bill" class="btn btn-success" />
                </div>
            </form>
        </div>
    </div>

    <% }%>

    <% if (request.getAttribute("Bill") != null) {%>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">            
            <form method="post" action="<%=application.getContextPath()%>/Front?direction=Bill&action=Update&patient=${Patient.id}">
                <div class="form-group">
                    <label for="fee" class="form-label">Consultation Fee</label>
                <input type="number" name="fee" value="${Bill.consultationFee}" class="form-control"/>
                </div>
                <div class="form-group">
                    <input type="submit" value="Update" class="btn btn-success" />
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <table class="table">
                <h3>Medicine</h3>
                <thead>
                    <tr>
                        <th width="20%">Id</th>
                        <th width="30%">Name</th>
                        <th width="20%">Cost</th>
                        <th width="30%">Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    <x:forEach items="${Bill.medicines}" var="medicine">
                        <tr>
                            <td><x:out value="${medicine.medicineId}" /></td>
                            <td><x:out value="${medicine.name}" /></td>
                            <td><x:out value="${medicine.cost}" /></td>
                            <td><x:out value="${medicine.quantity}" /></td>
                        </tr>
                    </x:forEach>
                </tbody>
            </table>
            <h4>Total cost = ${TotalCost}</h4>
            <br>
            <a href="<%=application.getContextPath()%>/Front?direction=Bill&action=Pay&bill=${Bill.id}">
                <button type="button" class="btn btn-primary">Pay</button></a>
        </div>
    </div>

    <% }%>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>