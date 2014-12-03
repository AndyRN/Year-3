<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="x" %>
<div class="container" style="margin-top: 20px;">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <table class="table">
                <h3>Patients</h3>
                <thead>
                    <tr>
                        <th width="20%">Id</th>
                        <th width="50%">Name</th>
                        <th width="30%">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <x:forEach items="${Patients}" var="patient">
                        <tr>
                            <td><x:out value="${patient.id}" /></td>
                            <td><x:out value="${patient.name}" /></td>
                            <td>
                                <a href="<%=application.getContextPath()%>/Front?direction=Patient&action=View&id=<x:out value="${patient.id}" />">
                                    <button type="button" class="btn btn-info">View</button></a>
                                <a href="<%=application.getContextPath()%>/Front?direction=Patient&action=Delete&id=<x:out value="${patient.id}" />">
                                    <button type="button" class="btn btn-danger">Delete</button></a>
                            </td>
                        </tr>
                    </x:forEach>
                </tbody>
            </table>
            <a href="<%=application.getContextPath()%>/Front?direction=Patient&action=Create">
                <button type="button" class="btn btn-primary">Add</button></a>
            <br>
            <br>
            <br>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <table class="table">
                <h3>Medicine</h3>
                <thead>
                    <tr>
                        <th width="15%">Id</th>
                        <th width="30%">Name</th>
                        <th width="15%">Cost</th>
                        <th width="40%">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <x:forEach items="${Medicines}" var="medicine">
                        <tr>
                            <td><x:out value="${medicine.id}" /></td>
                            <td><x:out value="${medicine.name}" /></td>
                            <td><x:out value="${medicine.cost}" /></td>
                            <td>
                                <a href="<%=application.getContextPath()%>/Front?direction=Medicine&action=View&id=<x:out value="${medicine.id}" />">
                                    <button type="button" class="btn btn-info">View</button></a>
                                <a href="<%=application.getContextPath()%>/Front?direction=Bill&action=AddToPatient&id=<x:out value="${medicine.id}" />&medicineName=${medicine.name}">
                                    <button type="button" class="btn btn-primary">Add To Patient</button></a>
                                <a href="<%=application.getContextPath()%>/Front?direction=Medicine&action=Delete&id=<x:out value="${medicine.id}" />">
                                    <button type="button" class="btn btn-danger">Delete</button></a>
                            </td>
                        </tr>
                    </x:forEach>
                </tbody>
            </table>
            <a href="<%=application.getContextPath()%>/Front?direction=Medicine&action=Create">
                <button type="button" class="btn btn-primary">Add</button></a>
            <br>
            <br>
            <br>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>