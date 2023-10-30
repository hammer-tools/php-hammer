package net.rentalhost.plugins.php.hammer.extensions.psi

import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.PhpAttribute
import com.jetbrains.php.lang.psi.elements.PhpAttributesList

fun PhpAttribute.getEntire() =
  with(getParentAttributesList()) {
    if (this?.attributes?.size == 1) this
    else this@getEntire
  }

fun PhpAttribute?.getParentAttributesList() =
  PhpPsiUtil.getParentOfClass(this, PhpAttributesList::class.java)
