<%@ page import="net.datafaker.Faker" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Random" %>
<%@ page import="ir.imorate.imoreport.Employee" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="fa">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link href="../style.css" rel="stylesheet">
    <title>Document</title>
</head>
<body>
<div class="container" id="container">
    <button id="download-button">Download as PDF</button>
    <div id="pdf">
        <table class="report-table">
            <tr class="text-center text-bold" style="font-size: 30px">
                <td colspan="3">سیستم تست - گزارش گیری</td>
            </tr>
            <tr>
                <td rowspan="5">
                    <img src="https://img.icons8.com/?size=128&id=0lg0kb05hrOz&format=png" alt="">
                </td>
            </tr>
            <tr>
                <td class="text-left" colspan="2">
                    <img class="ignore" src="https://img.icons8.com/?size=32&id=39977&format=png"
                         alt="Back">
                    <img class="ignore" src="https://img.icons8.com/?size=32&id=39977&format=png"
                         alt="Back">
                </td>
            </tr>
            <tr>
                <td class="text-left">
                    تاریخ گزارش:
                </td>
                <td>
                    1402/3/1
                </td>
            </tr>
            <tr>
                <td class="text-left">
                    گزارش از:
                </td>
                <td>
                    1402/3/1
                </td>
            </tr>
            <tr>
                <td class="text-left">
                    گزارش تا:
                </td>
                <td>
                    1402/3/1
                </td>
            </tr>
        </table>
        <table class="report-table" id="main-table">
            <thead>
            <tr>
                <th>نام و نام خانوادگی</th>
                <th>تاریخ تولد</th>
                <th>پرداختی</th>
                <th>اضافی</th>
                <th>کشور</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Employee> employeeList = (List<Employee>) request.getAttribute("employees");
                for (Employee employee : employeeList) {
                    out.print("            <tr>\n" +
                            "                <td>" + employee.getName() + "\n" +
                            "                <td>" + employee.getBirthDate() + "\n" +
                            "                <td>" + employee.getPayment() + "\n" +
                            "                <td>" + employee.getBonus() + "\n" +
                            "                <td>" + employee.getCountry() + "\n" +
                            "            </tr>\n");
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
<script src="https://unpkg.com/jspdf@latest/dist/jspdf.umd.min.js"></script>
<script>
    window.jsPDF = window.jspdf.jsPDF;
    function Convert_HTML_To_PDF() {
        const doc = new jsPDF({
            orientation: 'portrait',
            unit: 'px',
            compress: true,
            hotfixes: ["px_scaling"]
        });
        doc.setProperties({
            title: 'Title',
            subject: 'This is the subject',
            author: 'Author Name',
            keywords: 'generated, javascript, web 2.0, ajax',
            creator: 'Creator Name'
        });
        const elementHTML = document.querySelector("#pdf");
        doc.html(elementHTML, {
            callback: function (doc) {
                // Save the PDF
                doc.save('document-html.pdf');
            },
            fontFaces: [
                {
                    family: 'B Nazanin',
                    style: 'normal',
                    weight: 'normal',
                    stretch: 'normal',
                    src: [
                        {url: 'B-Nazanin.ttf', format: "truetype"},
                    ]
                },
                {
                    family: 'B Nazanin',
                    style: 'normal',
                    weight: 'bold',
                    stretch: 'normal',
                    src: [
                        {url: 'B-Nazanin-Bold.ttf', format: "truetype"}
                    ]
                },
                {
                    family: 'Calibri',
                    style: 'normal',
                    weight: 'normal',
                    stretch: 'normal',
                    src: [
                        {url: 'Calibri-Regular.ttf', format: "truetype"}
                    ]
                }
            ],
            margin: 5,
            autoPaging: 'text',
            x: 0,
            y: 0,
            width: 780,
            windowWidth: 1295
        });
        console.table(doc.getFontList());
    }

    const button = document.getElementById('download-button');
    button.addEventListener('click', Convert_HTML_To_PDF);
</script>
</body>
</html>