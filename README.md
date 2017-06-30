# httpserver
A simple android http server based on [nanohttpd](http://nanohttpd.org), support file browse, download and upload.
![file_upload](https://github.com/mason-Wang/httpserver/blob/master/img/file_upload.png)

## File Browse
We can browse phone sdcard contents on web browser, just type the server url on browser. For example, on my phone, type `http://10.17.63.119:8080`, the contents show like below:  
![file_browse](https://github.com/mason-Wang/httpserver/blob/master/img/file_browse.png)

## File Download
When browsing files, if the link is a directory, then browse will show all the contents under the directory; If the link is a file, when click the link, the file will be download by browser. 

## File Upload
We can upload files to our phone, uploaded files is stored at /sdcard/Download directory. This feature can be test by `curl` tool. For exmaple, upload `test.txt` file in current directory, use   
`curl -F file=@./test.txt http://10.17.63.119:8080`

Welcome Star and Fork!
