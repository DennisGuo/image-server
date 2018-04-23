(function ($, page,T) {

    $(init);

    function init() {
        router();
    }

    function router() {
        page('/', index);
        page('/explore', explore);

        page();

        page('/');
    }

    //首页
    function index() {
        load('index', function () {
            var form = $('#uploadForm');
            form.ajaxForm({
                success: function (responseText, statusText, xhr, $form) {
                    form.hide();
                    var rs = $('#uploadResult');
                    rs.html('<pre><code>' + JSON.stringify(responseText) + '</code></pre>');
                    rs.show();
                    renderPayloadTextarea(responseText);
                }
            });
            $('.btn-post').click(toPostImage);
        });
    }

    //浏览数据
    function explore() {
        load('explore', function () {
            loadData(0);
        });
    }

    function loadData(page){
        var url = '/image?page='+page+'&size=10';
        $.get(url,function(res){
            var box = $('.image-list');
            if(res && res.success){
                box.html(T.render($('#temp-image-card').html(),res.data));
            }else{
                box.html('<div class="text-center bg-muted" >No data.</div>');
            }
        });
    }

    function toPostImage() {
        var area = $('textarea[name=content]');
        var text = area.val();
        if (!text.trim()) {
            alert('please input the image info content.');
        }
        var payload = JSON.parse(text);

        $.ajax({
            url: '/image',
            method: 'POST',
            contentType: 'application/json',
            data: payload,
            dataType: 'json',
            success: function (res) {
                area.hide();
                var rs =  $('#postResult');
                rs.html('<pre><code>' + JSON.stringify(res) + '</code></pre>');
                rs.show();
            }
        });

    }

    function renderPayloadTextarea(response) {
        var res = JSON.parse(response);

        if (res.data) {
            res.data['content'] = {
                "source": "测绘局",
                "level": "一级",
                "coordinate": [11.1, 22.2, 33.3, 44.4]
            };
            $('textarea[name="content"]').text(JSON.stringify(res.data));
        }

    }

    //加载片段
    function load(name, callback) {
        $('#content').load('/partials/' + name + '.html', callback);
    }

}(window.jQuery, window.page,window.Tangular));