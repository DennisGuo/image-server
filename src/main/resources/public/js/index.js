(function ($, Sammy,T) {
    var app;

    $(init);

    function init() {
        router();
    }

    function router() {

        app = Sammy('#content',function(){
            var self = this;

            self.get('#/',index);
            self.get('#/explore',explore);

            self.after(renderMenu);
        });

        app.run('#/');
    }

    function renderMenu(){
        var hash = window.location.hash;
        $('.nav-link[href="'+hash+'"]').parent('.nav-item').addClass('active').siblings().removeClass('active');
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
            var info = $('.data-info');
            if(res && res.success){
                if(res.data && res.data.rows.length >0){
                    for(var i =0 ;i<res.data.rows.length;i++){
                        var item = res.data.rows[i];
                        res.data.rows[i]['contentKeys'] = Object.keys(item.content);
                    }
                }
                box.html(T.render($('#temp-image-card').html(),res.data));
                info.html('<span class="text-muted">total='+res.data.total+' ; pages='+res.data.totalPage+'</span>');
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

}(window.jQuery, window.Sammy,window.Tangular));