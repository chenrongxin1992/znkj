$(function(){
  $(document).on('hover', '#navigator .main .main-item', function(){
    $(this).addClass('hover').prev('.main-item').addClass('prev');
  }).on('mouseleave', '#navigator .main .main-item', function(){
    $(this).removeClass('hover').prev('.main-item.prev').removeClass('prev');
  }).on('click', '#search a', function(){
    return searchByWord($('#search input').val());
  }).on('keydown', '#search input', function(e){
    if(e.which === 13){
      // 回车搜索
      return searchByWord($(this).val());
    }
  });
  
  function searchByWord(keyword){
    window.location.href = '/search?q=' + keyword;
  }
  
  $('#language input').each(function(){
    this.onclick = function(){
      setLocaleLanguage(this.value);
    };
  });
  
  function setLocaleLanguage(lang){
    $.get('/other/setLanguage', {
      language: $.inArray(lang, ['zh', 'en'])
    }, function(data){
      if(data.success){
        return window.location.reload();
      }
    }, 'json');
  }
});