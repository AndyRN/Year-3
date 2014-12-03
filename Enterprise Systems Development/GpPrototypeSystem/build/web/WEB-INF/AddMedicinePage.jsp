<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="x" %>
<div class="container" style="margin-top: 20px;">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <h1>Add To Patient's Bill</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <h3>${MedicineName}</h3>
            <form method="post" action="<%=application.getContextPath()%>/Front?direction=Bill&action=AddMedicine&medicineId=${MedicineId}">
                <div class="form-group">
                    <label for="patient" class="form-label">Patient</label>
                    <select name="patient" class="form-control" >
                        <x:forEach items="${PatientsWithBill}" var="patient">
                            <option value="${patient.id}">${patient.name}</option>
                        </x:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="quantity" class="form-label">Quantity</label>
                    <input type="number" name="quantity" value="" class="form-control"/>
                </div>
                <div class="form-group">
                    <input type="submit" value="<x:out value="Add" />" class="btn btn-success" />
                    <a href="<%=application.getContextPath()%>/Front?direction=Bill&action=Cancel">
                        <button type="button" class="btn btn-default">Cancel</button></a>
                </div>
            </form>
        </div>
    </div>                
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>